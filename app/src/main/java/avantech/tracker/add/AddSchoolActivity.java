package avantech.tracker.add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import avantech.tracker.BusAttendantActivity;
import avantech.tracker.LoginActivity;
import avantech.tracker.R;
import avantech.tracker.model.SchoolModel;
import avantech.tracker.model.StudentModel;
import avantech.tracker.model.UsersModel;

public class AddSchoolActivity extends AppCompatActivity {
    EditText schoolName;
    EditText Id;
    EditText schoolEmail;
    EditText schoolPassword;
    EditText Type;

    String userType;
    String schoolId;

    Button submitSchoolDetails;

    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirebaseFirestore;
    CollectionReference mSchoolCollection;
    CollectionReference mUserCollectionRef;

    UsersModel schoolModelForUserDatabase;
    SchoolModel schoolModelForSchoolDatabase;

    private static final String TAG = "AddSchoolActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school);
        schoolName = findViewById(R.id.schoolName);
        Id = findViewById(R.id.schoolId);
        schoolEmail = findViewById(R.id.schoolEmail);
        schoolPassword = findViewById(R.id.schoolPassword);
        Type = findViewById(R.id.userType);

        submitSchoolDetails = findViewById(R.id.btnSubmitSchoolDetails);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mSchoolCollection = mFirebaseFirestore.collection("Schools");
        mUserCollectionRef = mFirebaseFirestore.collection("Users");

        Intent intentToGetUserType = getIntent();
        userType = intentToGetUserType.getStringExtra("userType");

        Intent intentToGetSchoolId = getIntent();
        schoolId = intentToGetSchoolId.getStringExtra("schoolId");

        Log.d(TAG, "UseType and SchoolId Through intent is : " + userType + " " + schoolId);

        submitSchoolDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = schoolEmail.getText().toString().trim();
                String pass = schoolPassword.getText().toString();

                String name = schoolName.getText().toString();
                String schoolId = Id.getText().toString();
                String userType = Type.getText().toString();

                schoolModelForSchoolDatabase =
                        new SchoolModel(name, schoolId, email, pass, userType);

                schoolModelForUserDatabase =
                        new UsersModel(email,pass, userType, schoolId);
                addUserForAuthentication(email, pass);
            }
        });
    }

    private void addUserForAuthentication(String email, String pass) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddSchoolActivity.this, "signInWithEmailPass : Successful", Toast.LENGTH_SHORT).show();
                            addUserInfoToFirebaseUsersCollection(schoolModelForUserDatabase);
                            addUserInfoToFirestoreSchoolCollection(schoolModelForSchoolDatabase);
                            clearAllFields();
                        } else {
                            Toast.makeText(AddSchoolActivity.this, "signInWithEmailPass : Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addUserInfoToFirestoreSchoolCollection(SchoolModel schoolModelForSchoolDatabase) {
        mSchoolCollection
                .document(schoolModelForSchoolDatabase.getSchoolId())
                .set(schoolModelForSchoolDatabase)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Added data at School Successfully");
                        Toast.makeText(AddSchoolActivity.this, "Added data to school database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.e(TAG, "Failed to add data in Schools : " + e.getMessage());
                    }
                });
    }

    private void clearAllFields() {
        schoolName.setText("");
        schoolEmail.setText("");
        schoolPassword.setText("");
        Type.setText("");
        Id.setText("");
        schoolModelForUserDatabase = new UsersModel();
        schoolModelForSchoolDatabase = new SchoolModel();
    }

    private void addUserInfoToFirebaseUsersCollection(UsersModel schoolModelForUserDatabase) {
        mUserCollectionRef
                .document(schoolModelForUserDatabase.getEmailId())
                .set(schoolModelForUserDatabase)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddSchoolActivity.this, "Data added to users collection", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddSchoolActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
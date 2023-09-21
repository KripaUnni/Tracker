package avantech.tracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import avantech.tracker.R;
import avantech.tracker.adapter.MyAdapter;
import avantech.tracker.model.StudentModel;

public class ViewStudentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<StudentModel> studentData;
    FirebaseFirestore mFirebaseFirestore;
    MyAdapter myAdapter;

    String schoolId;

    private static final String TAG = "ViewBusActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        recyclerView = findViewById(R.id.viewStudentRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentData = new ArrayList<>();
        myAdapter = new MyAdapter(studentData, getApplicationContext());
        recyclerView.setAdapter(myAdapter);

        Intent intent = getIntent();
        schoolId = intent.getStringExtra("schoolId");

        mFirebaseFirestore = FirebaseFirestore.getInstance();

        mFirebaseFirestore.collection("Schools")
                .document(schoolId)
                .collection("Students")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            StudentModel studentModel = d.toObject(StudentModel.class);
                            studentData.add(studentModel);
                            Log.d(TAG, studentModel.getStudentName());
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                });
    }
}
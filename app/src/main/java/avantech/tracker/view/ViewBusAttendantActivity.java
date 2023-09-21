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
import avantech.tracker.adapter.ViewBusAttendantAdapter;
import avantech.tracker.model.BusAttendantModel;

public class ViewBusAttendantActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<BusAttendantModel> busAttendantData;
    FirebaseFirestore mFirebaseFirestore;
    ViewBusAttendantAdapter viewBusAttendantAdapter;

    String schoolId;

    private final static String TAG = "ViewBusAttendant";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bus_attendant);
        recyclerView = findViewById(R.id.viewBusAttendantRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        busAttendantData = new ArrayList<>();
        viewBusAttendantAdapter = new ViewBusAttendantAdapter(busAttendantData, getApplicationContext());
        recyclerView.setAdapter(viewBusAttendantAdapter);

        Intent intent = getIntent();
        schoolId = intent.getStringExtra("schoolId");

        mFirebaseFirestore = FirebaseFirestore.getInstance();

        mFirebaseFirestore.collection("Schools")
                .document(schoolId)
                .collection("BusAttendant")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            BusAttendantModel busAttendantModel = d.toObject(BusAttendantModel.class);
                            busAttendantData.add(busAttendantModel);
                            Log.d(TAG, busAttendantModel.getName());
                        }
                        viewBusAttendantAdapter.notifyDataSetChanged();
                    }
                });
    }
}
package com.example.bmc208;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PatientViewBatch extends AppCompatActivity {

    DrawerLayout drawerLayout;
    DocumentReference docref;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_batch);

        drawerLayout = findViewById(R.id.drawer_layout_patient);

        // Create a reference to the cities collection
        String vaccine = "PFIZER_BATCH";
        String center = "Pfizer Center";

        RecyclerView recyclerView = findViewById(R.id.recycler_view_patient_batch_id);

        PatientBatchID[] batches; //= {
        //new PatientBatchID("P10000", "12/12/2021","10")
        //};

        CollectionReference batch = db.collection(vaccine);

        // Create a query against the collection.
        Query query = batch.whereEqualTo("center", center);


        ArrayList<PatientBatchID> Batches = new ArrayList<PatientBatchID>();

        db.collection(vaccine)
                .whereEqualTo("center", center)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Batches.add(new PatientBatchID(document.getString("batchID"), document.getString("date"),document.getString("quantity")));
                            }
                            PatientBatchIDAdapter adapter = new PatientBatchIDAdapter(Batches);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
    }


    public void ClickMenu(View view){
        PatientViewVaccine.openPatientDrawer(drawerLayout);
    }

    public void clickLogo(View view){
        PatientViewVaccine.closePatientDrawer(drawerLayout);
    }

    public void scheduleAppointment(View view){
        PatientViewVaccine.redirectPatientActivity(this, PatientViewVaccine.class);
    }

    public void status(View view){
        PatientViewVaccine.redirectPatientActivity(this, PatientStatus.class);
    }

    public void ClickLogout(View view){
        PatientViewVaccine.logoutPatient(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PatientViewVaccine.closePatientDrawer(drawerLayout);
    }
}
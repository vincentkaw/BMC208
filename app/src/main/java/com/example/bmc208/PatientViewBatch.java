package com.example.bmc208;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
        RecyclerView recyclerView = findViewById(R.id.recycler_view_patient_batch_id);

        String batch = "";

        Bundle extras = getIntent().getExtras();

            String center = extras.getString("center");
            String vaccine = extras.getString("vaccine");
            String email = extras.getString("patientEmail");
            if (vaccine.equals("PFIZER")){
                batch = "PFIZER_BATCH";
            }else if (vaccine.equals("SINO")){
                batch = "SINO_BATCH";
            }else if (vaccine.equals("ASTRA")){
                batch = "ASTRA_BATCH";
            }
            Toast.makeText(PatientViewBatch.this, vaccine, Toast.LENGTH_SHORT).show();

            ArrayList<PatientBatchID> Batches = new ArrayList<PatientBatchID>();

            db.collection(batch)
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
                                ArrayList<PatientBatchID> Batches = new ArrayList<PatientBatchID>();
                            }
                        }
                    });


        // Create a reference to the cities collection
        //String vaccine = "PFIZER_BATCH";
        //String center = "Pfizer Center";





        //CollectionReference batch = db.collection(vaccine);

        // Create a query against the collection.
        //Query query = batch.whereEqualTo("center", center);



        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(recyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(PatientViewBatch.this, AppointmentCalendar.class);
                //We have to pass key-value parameters
                intent.putExtra("PatientBatchID", Batches.get(position));
                intent.putExtra("patientEmail", email);
                intent.putExtra("center", center);
                intent.putExtra("vaccine", vaccine);
                startActivity(intent);
            }
        }));

    }



    public void ClickMenu(View view){
        PatientViewVaccine.openPatientDrawer(drawerLayout);
    }

    public void clickLogo(View view){
        PatientViewVaccine.closePatientDrawer(drawerLayout);
    }

    public void scheduleAppointment(View view){
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("patientEmail");
        Intent back = new Intent(PatientViewBatch.this, PatientViewVaccine.class);
        back.putExtra("patientEmail", email);
        startActivity(back);
    }

    public void status(View view){
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("patientEmail");
        Intent back = new Intent(PatientViewBatch.this, PatientStatus.class);
        back.putExtra("patientEmail", email);
        startActivity(back);
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
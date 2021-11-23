package com.example.bmc208;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PatientViewBatch extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    DrawerLayout drawerLayout;
    DocumentReference docref;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_batch);

        drawerLayout = findViewById(R.id.drawer_layout_patient);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_patient_batch_id);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("patientUsername");
        String password = extras.getString("patientPassword");

        String batch = "";


        if (extras != null){
            String center = extras.getString("center");
            String vaccine = extras.getString("vaccine");
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


        }

        // Create a reference to the cities collection
        //String vaccine = "PFIZER_BATCH";
        //String center = "Pfizer Center";





        //CollectionReference batch = db.collection(vaccine);

        // Create a query against the collection.
        //Query query = batch.whereEqualTo("center", center);



        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(recyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        }));

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());


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
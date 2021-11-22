package com.example.bmc208;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

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
package com.example.bmc208;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PatientStatus extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView vaccinationID;
    TextView appointmentStatus;
    TextView appointmentDate;
    TextView appointmentCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_status);

        drawerLayout = findViewById(R.id.drawer_layout_patient);
        vaccinationID = findViewById(R.id.text_view_vaccination_id);
        appointmentStatus = findViewById(R.id.text_view_status);
        appointmentDate = findViewById(R.id.text_view_appointment_date);
        appointmentCenter = findViewById(R.id.text_view_appointment_center);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("patientEmail");

        db.collection("Appointment")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                vaccinationID.setText(document.getString("vaccinationID"));
                                appointmentStatus.setText(document.getString("status"));
                                appointmentCenter.setText(document.getString("centerName"));
                                appointmentDate.setText(document.getString("appointmentDate"));
                            }

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
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("patientEmail");
        //PatientViewVaccine.redirectPatientActivity(this, PatientViewVaccine.class);
        Intent back = new Intent(PatientStatus.this, PatientViewVaccine.class);
        back.putExtra("patientEmail", email);
        startActivity(back);
    }

    public void status(View view){
        recreate();
    }

    public void ClickLogout(View view){
        PatientViewVaccine.redirectPatientActivity(this, signup.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PatientViewVaccine.closePatientDrawer(drawerLayout);
    }
}
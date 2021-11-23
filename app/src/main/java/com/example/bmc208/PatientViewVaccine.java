package com.example.bmc208;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class PatientViewVaccine extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Button pfizer;
    Button Sino;
    Button Astra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_vaccine);

        drawerLayout = findViewById(R.id.drawer_layout_patient);
        pfizer = findViewById(R.id.button_patient_pfizer_vaccine);
        Sino = findViewById(R.id.button_patient_sino_vaccine);
        Astra = findViewById(R.id.button_patient_astra_vaccine);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("patientEmail");


        pfizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vaccine = "PFIZER";
                Intent center = new Intent(PatientViewVaccine.this, PatientViewCenter.class);
                center.putExtra("vaccine", vaccine);
                center.putExtra("patientEmail", email);
                startActivity(center);
            }
        });

        Sino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vaccine ="SINO";
                Intent center = new Intent(PatientViewVaccine.this, PatientViewCenter.class);
                center.putExtra("vaccine", vaccine);
                center.putExtra("patientEmail", email);
                startActivity(center);
            }
        });

        Astra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vaccine ="ASTRA";
                Intent center = new Intent(PatientViewVaccine.this, PatientViewCenter.class);
                center.putExtra("vaccine", vaccine);
                center.putExtra("patientEmail", email);
                startActivity(center);
            }
        });
    }

    public void ClickMenu(View view){
        openPatientDrawer(drawerLayout);
    }

    public static void openPatientDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void clickLogo(View view) {
        closePatientDrawer(drawerLayout);
    }

    public static void closePatientDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void scheduleAppointment(View view){
        recreate();
    }

    public void status(View view){
        //redirectPatientActivity(this, PatientStatus.class);
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("patientEmail");
        Intent back = new Intent(PatientViewVaccine.this, PatientStatus.class);
        back.putExtra("patientEmail", email);
        startActivity(back);
    }

    public void ClickLogout(View view){
        redirectPatientActivity(this, signup.class);
    }


    public static void redirectPatientActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closePatientDrawer(drawerLayout);
    }
}
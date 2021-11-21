package com.example.bmc208;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class PatientStatus extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_status);

        drawerLayout = findViewById(R.id.drawer_layout_patient);
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
        recreate();
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
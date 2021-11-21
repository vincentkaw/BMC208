package com.example.bmc208;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PatientViewCenter extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView vaccineName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_center);
        drawerLayout = findViewById(R.id.drawer_layout_patient);
        vaccineName = findViewById(R.id.text_view_vaccine_name);
        Bundle extras = getIntent().getExtras(); //bundle to receive extra
        if (extras != null) { //get vaccine name from selecting vaccine
            String value = extras.getString("vaccine");
            vaccineName.setText(value);
        }

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
package com.example.bmc208;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PatientViewVaccine extends AppCompatActivity {

    DrawerLayout drawerLayout;

    public static void OpenDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_vaccine);

        drawerLayout = findViewById(R.id.drawer_layout_patient);
    }

    public void ClickMenu(View view){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void scheduleAppointment(){
        recreate();
    }

    public void status(){
        redirectActivity(this, PatientStatus.class);
    }

    private void redirectActivity(PatientViewVaccine patientViewVaccine, Class<PatientStatus> patientStatusClass) {
        Intent intent = new Intent(patientViewVaccine, patientStatusClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        patientViewVaccine.startActivity(intent);
    }

    public void ClickLogout(View view){
        logout(this);
    }

    private void logout(PatientViewVaccine patientViewVaccine) {
        //Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(patientViewVaccine);
        //Set title
        builder.setTitle("Logout");
        //Set message
        builder.setMessage("Are you sure you want to logout?");
        //Positive yes button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Finish activity
                patientViewVaccine.finishAffinity();
                //Exit app
                System.exit(0);
            }
        });
        //Negative no button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();
            }
        });
        //Show dialog
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}
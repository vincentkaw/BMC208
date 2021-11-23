package com.example.bmc208;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PatientViewCenter extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView vaccineName;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_center);
        drawerLayout = findViewById(R.id.drawer_layout_patient);
        vaccineName = findViewById(R.id.text_view_vaccine_name);

        ListView centerList = findViewById(R.id.listview_patient_view_center);
        List<String> centers = new ArrayList<String>();
        centers.add("dummy");

        Bundle extras = getIntent().getExtras(); //bundle to receive extra
        if (extras != null) { //get vaccine name from selecting vaccine
            String value = extras.getString("vaccine");
            vaccineName.setText(value);
            String email = extras.getString("patientEmail");

            if (vaccineName.getText().toString().equals("PFIZER")) {
                db.collection("PFIZER_BATCH")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String flag = "not same";
                                        for (int i = 0; i < centers.size(); i++) {
                                            if (document.getString("center").equals(centers.get(i))) {
                                                flag = "same";
                                            }
                                        }
                                        if (flag.equals("not same")) {
                                            centers.add(document.getString("center"));
                                        }
                                    }
                                    centers.remove(0);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PatientViewCenter.this, android.R.layout.simple_list_item_1, centers);
                                    centerList.setAdapter(adapter);
                                    centerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String selectedCenter = (String) parent.getItemAtPosition(position);
                                            Intent batch = new Intent(PatientViewCenter.this, PatientViewBatch.class);
                                            batch.putExtra("center", selectedCenter);
                                            batch.putExtra("vaccine", vaccineName.getText().toString());
                                            batch.putExtra("patientEmail", email);
                                            startActivity(batch);
                                        }
                                    });
                                }
                            }
                        });
            }
            if (vaccineName.getText().toString().equals("SINO")) {
                db.collection("SINO_BATCH")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String flag = "not same";
                                        for (int i = 0; i < centers.size(); i++) {
                                            if (document.getString("center").equals(centers.get(i))) {
                                                flag = "same";
                                            }
                                        }
                                        if (flag.equals("not same")) {
                                            centers.add(document.getString("center"));
                                        }
                                    }
                                    centers.remove(0);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PatientViewCenter.this, android.R.layout.simple_list_item_1, centers);
                                    centerList.setAdapter(adapter);
                                    centerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String selectedCenter = (String) parent.getItemAtPosition(position);
                                            Intent batch = new Intent(PatientViewCenter.this, PatientViewBatch.class);
                                            batch.putExtra("center", selectedCenter);
                                            batch.putExtra("vaccine", vaccineName.getText().toString());
                                            batch.putExtra("patientEmail", email);
                                            startActivity(batch);
                                        }
                                    });
                                }
                            }
                        });

            }
            if (vaccineName.getText().toString().equals("ASTRA")) {
                db.collection("ASTRA_BATCH")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String flag = "not same";
                                        for (int i = 0; i < centers.size(); i++) {
                                            if (document.getString("center").equals(centers.get(i))) {
                                                flag = "same";
                                            }
                                        }
                                        if (flag.equals("not same")) {
                                            centers.add(document.getString("center"));
                                        }
                                    }
                                    centers.remove(0);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PatientViewCenter.this, android.R.layout.simple_list_item_1, centers);
                                    centerList.setAdapter(adapter);
                                    centerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String selectedCenter = (String) parent.getItemAtPosition(position);
                                            Intent batch = new Intent(PatientViewCenter.this, PatientViewBatch.class);
                                            batch.putExtra("center", selectedCenter);
                                            batch.putExtra("vaccine", vaccineName.getText().toString());
                                            batch.putExtra("patientEmail", email);
                                            startActivity(batch);
                                        }
                                    });
                                }
                            }
                        });

            }
        }
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
        Intent back = new Intent(PatientViewCenter.this, PatientViewVaccine.class);
        back.putExtra("patientEmail", email);
        startActivity(back);
    }

    public void status(View view){
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("patientEmail");
        Intent back = new Intent(PatientViewCenter.this, PatientStatus.class);
        back.putExtra("patientEmail", email);
        startActivity(back);
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
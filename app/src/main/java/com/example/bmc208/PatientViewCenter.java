package com.example.bmc208;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

            if (vaccineName.getText().toString().equals("PFIZER")){
                db.collection("PFIZER_BATCH")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String flag = "not same";
                                        for (int i = 0; i < centers.size(); i++){
                                            if (document.getString("center").equals(centers.get(i))) {
                                               flag = "same";
                                               }
                                            }
                                        if (flag.equals("not same")){
                                            centers.add(document.getString("center"));
                                        }
                                    }
                                    centers.remove(0);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PatientViewCenter.this, android.R.layout.simple_list_item_1, centers);
                                    centerList.setAdapter(adapter);
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
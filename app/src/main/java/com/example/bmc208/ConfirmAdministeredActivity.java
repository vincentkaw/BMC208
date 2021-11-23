package com.example.bmc208;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.PrivateKey;

public class ConfirmAdministeredActivity extends AppCompatActivity {

    TextView batchID;
    TextView expiryDate;
    TextView manufacturer;
    TextView vaccineName;
    TextView name;
    TextView vaccineID;
    TextView ic;
    TextView remarksText;
    EditText remarks;
    Button reject;
    Button confirm;
    Button administered;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_administered);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Vaccine selectedVaccine = (Vaccine) getIntent().getSerializableExtra("Vaccine");
        Bundle extras = getIntent().getExtras();
        String center = extras.getString("adminCenter");
        String vaccine = extras.getString("vaccine");

        vaccineID = findViewById(R.id.vaccine_id_text_view);
        batchID = findViewById(R.id.batch_id_text_view);
        expiryDate = findViewById(R.id.expiry_date_text_view);
        manufacturer = findViewById(R.id.manufacture_name_text_view);
        vaccineName = findViewById(R.id.vaccine_text_view);
        name = findViewById(R.id.name_text_view);
        ic = findViewById(R.id.ic_text_view);
        remarksText = findViewById(R.id.remarks_text_view);
        remarks = findViewById(R.id.remarks_edit_text);
        reject = findViewById(R.id.reject_button);
        confirm = findViewById(R.id.confirm_button);
        administered = findViewById(R.id.administered_button);

        vaccineID.setText(selectedVaccine.vaccineID);

        String status = null;
        status = selectedVaccine.status;

        if (status.equals("Pending") || status.equals("Rejected")){
            reject.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
        }else if (status.equals("Confirmed") || status.equals("Administered")){
            remarksText.setVisibility(View.VISIBLE);
            remarks.setVisibility(View.VISIBLE);
            administered.setVisibility(View.VISIBLE);
        }

        CollectionReference deliveryRef = db.collection("Appointment");
        Query nameQuery = deliveryRef.whereEqualTo("vaccinationID", selectedVaccine.vaccineID);
        nameQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        id = document.getId();
                    }
                }
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference ref = db.collection("Appointment").document(id);
                ref.update(
                        "status", "Rejected"
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ConfirmAdministeredActivity.this, "Rejected", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ConfirmAdministeredActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Intent vaccineIDIntent = new Intent(ConfirmAdministeredActivity.this, administrator_batch.class);
                vaccineIDIntent.putExtra("adminCenter", center);
                vaccineIDIntent.putExtra("vaccine", vaccine);
                startActivity(vaccineIDIntent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference ref = db.collection("Appointment").document(id);
                ref.update(
                        "status", "Confirmed"
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ConfirmAdministeredActivity.this, "Confirmed", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ConfirmAdministeredActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Intent vaccineIDIntent = new Intent(ConfirmAdministeredActivity.this, administrator_batch.class);
                vaccineIDIntent.putExtra("adminCenter", center);
                vaccineIDIntent.putExtra("vaccine", vaccine);
                startActivity(vaccineIDIntent);
            }
        });

        administered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remarksData = remarks.getText().toString();
                DocumentReference ref = db.collection("Appointment").document(id);
                ref.update(
                        "status", "Administered",
                        "remarks", remarksData
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ConfirmAdministeredActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ConfirmAdministeredActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                Intent vaccineIDIntent = new Intent(ConfirmAdministeredActivity.this, administrator_batch.class);
                vaccineIDIntent.putExtra("adminCenter", center);
                vaccineIDIntent.putExtra("vaccine", vaccine);
                startActivity(vaccineIDIntent);
            }
        });

    }
}
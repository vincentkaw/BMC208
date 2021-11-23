package com.example.bmc208;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmAdministeredActivity extends AppCompatActivity {

    TextView batchID;
    TextView expiryDate;
    TextView manufacturer;
    TextView vaccineName;
    TextView name;
    TextView ic;
    TextView remarksText;
    EditText remarks;
    Button reject;
    Button confirm;
    Button administered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_administered);

        Vaccine selectedVaccine = (Vaccine) getIntent().getSerializableExtra("Vaccine");

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

        String status = null;

        status = selectedVaccine.status;

        if (status.equals("Pending")){
            reject.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
        }else if (status.equals("Confirmed") || status.equals("Administered")){
            remarksText.setVisibility(View.VISIBLE);
            remarks.setVisibility(View.VISIBLE);
            administered.setVisibility(View.VISIBLE);
        }

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vaccineIDIntent = new Intent(ConfirmAdministeredActivity.this, VaccineID.class);
                startActivity(vaccineIDIntent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vaccineIDIntent = new Intent(ConfirmAdministeredActivity.this, VaccineID.class);
                startActivity(vaccineIDIntent);
            }
        });

        administered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remarksData = remarks.getText().toString();
                Intent vaccineIDIntent = new Intent(ConfirmAdministeredActivity.this, VaccineID.class);
                startActivity(vaccineIDIntent);
            }
        });


    }
}
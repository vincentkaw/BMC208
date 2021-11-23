package com.example.bmc208;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AppointmentCalendar extends AppCompatActivity {

    DatePicker datePicker;
    Button setDate;
    TextView batchNo;
    TextView appointmentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_calendar);

        PatientBatchID selectedBatch = (PatientBatchID) getIntent().getSerializableExtra("PatientBatchID");


        datePicker = findViewById(R.id.date_picker_calendar);
        setDate = findViewById(R.id.set_date_button);
        batchNo = findViewById(R.id.batch_text_view);
        appointmentDate = findViewById(R.id.date_text_view);

        batchNo.setText(selectedBatch.batchID);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day=datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                appointmentDate.setText("Appointment Date: " + day + "/" + month + "/" + year);

                AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentCalendar.this);
                builder.setCancelable(true);
                builder.setMessage("The date u pick for " + batchNo.getText().toString() + " is " +  day + "/" + month + "/" + year );

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent statusActivityIntent = new Intent(AppointmentCalendar.this, PatientStatus.class);
                        startActivity(statusActivityIntent);
                    }
                });
                builder.show();
            }
        });
    }
}
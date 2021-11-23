package com.example.bmc208;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.UUID;

public class AppointmentCalendar extends AppCompatActivity {

    DatePicker datePicker;
    Button setDate;
    TextView batchNo;
    TextView appointmentDate;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_calendar);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("patientEmail");
        String center = extras.getString("center");

        Toast.makeText(AppointmentCalendar.this, email, Toast.LENGTH_SHORT).show();

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
                        Appointment appointment = new Appointment();
                        appointment.setVaccinationID(UUID.randomUUID().toString());
                        appointment.setAppointmentDate(day + "/" + month + "/" + year);
                        appointment.setCenterName(center);
                        appointment.setBatchID(batchNo.getText().toString());
                        appointment.setStatus("Pending");
                        appointment.setRemarks("");
                        appointment.setEmail(email);

                        db.collection("Appointment")
                                .whereEqualTo("email", email)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        String flag = "no appointment";
                                        if (task.isSuccessful()){
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                if (document.getString("email").equals(email)){
                                                    flag = "got appointment";
                                                    break;
                                                }
                                            }
                                            if (flag.equals("no appointment")){
                                                db.collection(Appointment.COLLECTION_NAME)
                                                        .document()
                                                        .set(appointment)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(AppointmentCalendar.this, "Appointment Successfully made", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(AppointmentCalendar.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }else if (flag.equals("got appointment")){
                                                Toast.makeText(AppointmentCalendar.this, "You have already made an appointment", Toast.LENGTH_SHORT).show();
                                                Intent status = new Intent(AppointmentCalendar.this, PatientStatus.class);
                                                status.putExtra("patientEmail", email);
                                                startActivity(status);
                                            }
                                        }
                                    }
                                });


                        Intent status = new Intent(AppointmentCalendar.this, PatientStatus.class);
                        status.putExtra("patientEmail", email);
                        startActivity(status);

                        //Intent statusActivityIntent = new Intent(AppointmentCalendar.this, PatientStatus.class);
                        //startActivity(statusActivityIntent);
                    }
                });
                builder.show();
            }
        });
    }
}
package com.example.bmc208;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class signup_patient extends AppCompatActivity {

    TextView textViewSignIn;
    EditText username;
    EditText password;
    EditText email;
    EditText icPassword;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_patient);

        username = findViewById(R.id.enter_username);
        password = findViewById(R.id.enter_password);
        email = findViewById(R.id.enter_email);
        icPassword = findViewById(R.id.enter_IC_passport);

        textViewSignIn = findViewById(R.id.text_signup);

        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup_patient.this, signinPatient.class));
            }
        });
    }

    public void SignUpButtonClick(View view) {

        Patient patient = new Patient();
        patient.setPatientid(UUID.randomUUID().toString());
        patient.setUsername(username.getText().toString());
        patient.setPassword(password.getText().toString());
        patient.setEmail(email.getText().toString());
        patient.setIc_passport(icPassword.getText().toString());

        db.collection(Patient.COLLECTION_NAME)
                .document()
                .set(patient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        signinPatient.Patient = patient;
                        startActivity(new Intent(signup_patient.this, signinPatient.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(signup_patient.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
package com.example.bmc208;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class signinPatient extends AppCompatActivity {

    TextView textViewSignUp;
    EditText username;
    EditText password;
    Button btnLogin;
    CheckBox showPassword;

    SharedPreferences sharedPreferences;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static Patient Patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_patient);

        username = findViewById(R.id.enter_username);
        password = findViewById(R.id.enter_password);
        btnLogin = findViewById(R.id.btn_login);
        textViewSignUp = findViewById(R.id.text_signup);
        showPassword = findViewById(R.id.show_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection(Patient.COLLECTION_NAME)
                        .whereEqualTo("username", username.getText().toString())
                        .whereEqualTo("password", password.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    Patient patient = documentSnapshot.toObject(Patient.class);
                                    Patient = patient;
                                    Intent vaccine = new Intent(signinPatient.this, PatientViewVaccine.class);
                                    String email = documentSnapshot.getString("email");
                                    vaccine.putExtra("patientEmail", email);
                                    startActivity(vaccine);
                                    //startActivity(new Intent(signinPatient.this, PatientViewVaccine.class));
                                    //break;
                                }

                                if(Patient == null){
                                    Toast.makeText(signinPatient.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                                }
                            }

                        });
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signinPatient.this, signup_patient.class));
            }
        });


    }

    public void ShowPasswordButtonClick(View view) {
        if (showPassword.isChecked()) {
            password.setTransformationMethod(null);
        } else {
            password.setTransformationMethod(new PasswordTransformationMethod());
        }
    }
}
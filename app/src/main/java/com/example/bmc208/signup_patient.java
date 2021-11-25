package com.example.bmc208;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

        String name = username.getText().toString();
        String passwords = password.getText().toString();
        String emails = email.getText().toString();
        String ic = icPassword.getText().toString();
        boolean check = validationinfo(name,passwords,emails,ic);

        if (check==true){
            db.collection(Patient.COLLECTION_NAME)
                    .whereEqualTo("email", emails)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            String flag = "not registered";
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                if (documentSnapshot.getString("email").equals(emails)){
                                    flag = "registered";
                                    break;
                                }
                            }
                            if (flag.equals("not registered")){
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
                                                Toast.makeText(getApplicationContext(), "Data is valid", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(signup_patient.this, signinPatient.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(signup_patient.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }else if (flag.equals("registered")){
                                Toast.makeText(signup_patient.this, "This email has already been registered", Toast.LENGTH_LONG).show();
                            }

                            //if(Patient == null){
                            //  Toast.makeText(signinPatient.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                            //}
                        }

                    });


        }
        else{
            Toast.makeText(getApplicationContext(), "Check your details again", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validationinfo(String name, String passwords, String emails, String ic) {

        if (name.length()==0){
            username.requestFocus();
            username.setError("Username cannot be empty");
            return false;
        }
        else if (passwords.length()<=6){
            password.requestFocus();
            password.setError("Password minimum must have 6 characters");
            return false;
        }
        else if (!emails.matches("[a-zA-Z0-9._]+@[a-z]+\\.+[a-z]+")){
            email.requestFocus();
            email.setError("Enter valid email");
            return false;
        }
        else if (ic.length()==0){
            icPassword.requestFocus();
            icPassword.setError("IC or Passport cannot be empty");
            return false;
        }
        else {
            return true;
        }
    }

}





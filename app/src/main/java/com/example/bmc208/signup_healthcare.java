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

public class signup_healthcare extends AppCompatActivity {

    TextView textViewSignIn;
    EditText username;
    EditText password;
    EditText fullname;
    EditText email;
    EditText staffid;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_healthcare);

        username = findViewById(R.id.enter_username);
        password = findViewById(R.id.enter_password);
        fullname = findViewById(R.id.enter_fullname);
        email = findViewById(R.id.enter_email);
        staffid = findViewById(R.id.enter_staffid);
        textViewSignIn = findViewById(R.id.text_signup);

        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup_healthcare.this, signinAdministrator.class));
            }
        });
    }

    public void SignUpButtonClick(View view) {

        Administrator administrator = new Administrator();
        administrator.setAdministratorid(UUID.randomUUID().toString());
        administrator.setUsername(username.getText().toString());
        administrator.setPassword(password.getText().toString());
        administrator.setFullname(fullname.getText().toString());
        administrator.setEmail(email.getText().toString());
        administrator.setStaffid(staffid.getText().toString());

        db.collection(Administrator.COLLECTION_NAME)
                .document()
                .set(administrator)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        signinAdministrator.Administrator = administrator;
                        startActivity(new Intent(signup_healthcare.this, signinAdministrator.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(signup_healthcare.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
package com.example.bmc208;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class signup_healthcare extends AppCompatActivity {

    TextView textViewAddCenter;
    TextView textViewSignIn;
    EditText username;
    EditText password;
    EditText fullname;
    EditText email;
    EditText staffid;

    Spinner spinner;
    TextView center_view;
    TextView address_view;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static AdministratorCenter AdministratorCenter;

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
        textViewAddCenter = findViewById(R.id.view_add_center);

        spinner = findViewById(R.id.spinner_center);
        center_view = findViewById(R.id.view_center);
        address_view = findViewById(R.id.view_address);
        
        textViewAddCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup_healthcare.this, signinAdministrator.class));
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                db.collection(AdministratorCenter.COLLECTION_NAME)
                        .whereEqualTo("center", spinner.getSelectedItem().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                }

                            }

                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }

    private void openDialog() {
        administrator_add_center addcenter = new administrator_add_center();
        addcenter.show(getSupportFragmentManager(), "add center dialog");
    }

    public void SignUpButtonClick(View view) {

        String name = username.getText().toString();
        String passwords = password.getText().toString();
        String fulln = fullname.getText().toString();
        String emails = email.getText().toString();
        String id = staffid.getText().toString();
        boolean check = validationinfo(name,passwords,fulln,emails,id);

        if (check==true){
            Administrator administrator = new Administrator();
            administrator.setAdministratorid(UUID.randomUUID().toString());
            administrator.setCentername(center_view.getText().toString());
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
                            Toast.makeText(getApplicationContext(), "Data is valid", Toast.LENGTH_LONG).show();
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
        else{
            Toast.makeText(getApplicationContext(), "Check your details again", Toast.LENGTH_LONG).show();
        }


    }

    private boolean validationinfo(String name, String passwords, String fulln, String emails, String id) {

        if (name.length()==0){
            username.requestFocus();
            username.setError("Username cannot be empty");
            return false;
        }
        else if (passwords.length()<=6){
            password.requestFocus();
            password.setError("Password minimum must have 6 charactors");
            return false;
        }
        else if (!emails.matches("[a-zA-Z0-9._]+@[a-z]+\\.+[a-z]+")){
            email.requestFocus();
            email.setError("Enter valid error");
            return false;
        }
        else if (fulln.length()==0){
            fullname.requestFocus();
            fullname.setError("Fullname cannot be empty");
            return false;
        }
        else if (id.length()<5){
            staffid.requestFocus();
            staffid.setError("Staff id cannot be empty");
            return false;
        }
        else {
            return true;
        }
    }

}
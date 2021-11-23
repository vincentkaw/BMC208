package com.example.bmc208;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    DatabaseReference dbref;

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
        dbref = FirebaseDatabase.getInstance().getReference("spinner");

//        ArrayAdapter<String> center_adapter = new ArrayAdapter<String>(signup_healthcare.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.center));
//        center_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(center_adapter);
        
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
    }

    private void openDialog() {
        final Dialog addcenter = new Dialog(signup_healthcare.this);
        addcenter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addcenter.setCancelable(true);
        addcenter.setContentView(R.layout.activity_administrator_add_center);

        final EditText centername = addcenter.findViewById(R.id.edit_centername);
        final EditText address = addcenter.findViewById(R.id.edit_address);
        Button btnSave = addcenter.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String center = centername.getText().toString();
                String addressCenter = address.getText().toString();
                showDetails(center,addressCenter);
                addcenter.dismiss();
            }
        });
        addcenter.show();
    }

    private void showDetails(String center, String addressCenter) {
        center_view.setText(String.format(center));
        address_view.setText(String.format(addressCenter));
    }

    public void SignUpButtonClick(View view) {
        String name = username.getText().toString();
        String passwords = password.getText().toString();
        String fulln = fullname.getText().toString();
        String emails = email.getText().toString();
        String id = staffid.getText().toString();
        boolean check = validationinfo(name,passwords,fulln,emails,id);

        if (check==true){

            db.collection(Administrator.COLLECTION_NAME)
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
                                                Toast.makeText(getApplicationContext(), "Data is valid", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(signup_healthcare.this, signinPatient.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(signup_healthcare.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }else if (flag.equals("registered")){
                                Toast.makeText(signup_healthcare.this, "This email has already been registered", Toast.LENGTH_LONG).show();
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
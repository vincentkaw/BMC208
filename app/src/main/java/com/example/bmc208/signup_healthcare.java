package com.example.bmc208;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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
    DatabaseReference dbref;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static AdministratorCenter AdministratorCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_healthcare);

        ValueEventListener listener;
        ArrayList<String> list = new ArrayList<String>();
        ArrayAdapter<String> adapter;

        username = findViewById(R.id.enter_username);
        password = findViewById(R.id.enter_password);
        fullname = findViewById(R.id.enter_fullname);
        email = findViewById(R.id.enter_email);
        staffid = findViewById(R.id.enter_staffid);
        textViewSignIn = findViewById(R.id.text_signup);
        textViewAddCenter = findViewById(R.id.view_add_center);

        center_view = findViewById(R.id.view_center);
        address_view = findViewById(R.id.view_address);

        spinner = findViewById(R.id.spinner_center);
        dbref = FirebaseDatabase.getInstance().getReference("name");

        //spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        db.collection("AdministratorCenter")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getString("name"));
                            }
                            ArrayAdapter<String> center_adapter = new ArrayAdapter<String>(signup_healthcare.this, android.R.layout.simple_spinner_item, list);
                            center_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(center_adapter);

                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String item = list.get(position);
                                    center_view.setText(item);

                                    CollectionReference deliveryRef = db.collection("AdministratorCenter");
                                    Query nameQuery = deliveryRef.whereEqualTo("name",item);
                                    nameQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    String id = document.getString("address");
                                                    address_view.setText(id);
                                                }
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    }
                });




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

        btnSave.setOnClickListener(view -> {
//            String center = centername.getText().toString();
//            String addressCenter = address.getText().toString();
//            showDetails(center,addressCenter);
//            addcenter.dismiss();
            AdministratorCenter administratorCenter = new AdministratorCenter();
            administratorCenter.setCenterid(UUID.randomUUID().toString());
            administratorCenter.setName(centername.getText().toString());
            administratorCenter.setAddress(address.getText().toString());

            if (centername.getText().toString().equals("") || address.getText().toString().equals("")){
                Toast.makeText(signup_healthcare.this, "Please fill in the details of the center fully", Toast.LENGTH_SHORT).show();
            }else {
                db.collection("AdministratorCenter").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String flag = "not same";
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getString("name").equals(centername.getText().toString())){
                                flag = "same";
                                break;
                            }
                        }
                        if (flag.equals("not same")){
                            db.collection(AdministratorCenter.COLLECTION_NAME)
                                    .document()
                                    .set(administratorCenter)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            signup_healthcare.AdministratorCenter = administratorCenter;
                                            String center = centername.getText().toString();
                                            String addressCenter = address.getText().toString();
                                            showDetails(center,addressCenter);
                                            addcenter.dismiss();
                                        }
                                    });
                        }
                    }
                });
            }


        });
        addcenter.show();
    }


    private void showDetails(String center, String addressCenter) {
        center_view.setText(String.format(center));
        address_view.setText(String.format(addressCenter));
    }

    public void SignUpButtonClick(View view) {
        final Dialog addcenter = new Dialog(signup_healthcare.this);
        addcenter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addcenter.setCancelable(true);
        addcenter.setContentView(R.layout.activity_administrator_add_center);
        final EditText centername = addcenter.findViewById(R.id.edit_centername);

        String name = username.getText().toString();
        String passwords = password.getText().toString();
        String fulln = fullname.getText().toString();
        String emails = email.getText().toString();
        String id = staffid.getText().toString();
        String center = center_view.getText().toString();
        boolean check = validationinfo(name,passwords,fulln,emails,id, center);

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
                                administrator.setCentername(center);

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

    private boolean validationinfo(String name, String passwords, String fulln, String emails, String id, String center) {

        if (center.length()==0){
            center_view.requestFocus();
            center_view.setError("You must select the center");
            return false;
        }
        else if (name.length()==0){
            username.requestFocus();
            username.setError("Username cannot be empty");
            return false;
        }
        else if (passwords.length()<=6){
            password.requestFocus();
            password.setError("Password minimum must have 6 charactors");
            return false;
        }
        else if (fulln.length()==0){
            fullname.requestFocus();
            fullname.setError("Fullname cannot be empty");
            return false;
        }
        else if (!emails.matches("[a-zA-Z0-9._]+@[a-z]+\\.+[a-z]+")){
            email.requestFocus();
            email.setError("Enter valid email");
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
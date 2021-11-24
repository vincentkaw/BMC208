package com.example.bmc208;

import android.content.Intent;
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

public class signinAdministrator extends AppCompatActivity {

    TextView textViewSignUp;
    EditText username;
    EditText password;
    TextView center;
    Button btnLogin;
    CheckBox showPassword;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static Administrator Administrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_administrator);

        username = findViewById(R.id.enter_username);
        password = findViewById(R.id.enter_password);
        btnLogin = findViewById(R.id.btn_login);
        textViewSignUp = findViewById(R.id.text_signup);
        showPassword = findViewById(R.id.show_password);
        center = findViewById(R.id.text);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection(Administrator.COLLECTION_NAME)
                        .whereEqualTo("username", username.getText().toString())
                        .whereEqualTo("password", password.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    Administrator administrator = documentSnapshot.toObject(Administrator.class);
                                    Administrator = administrator;
                                    String center = documentSnapshot.getString("centername");
                                    Intent adminCenter = new Intent(signinAdministrator.this, AddBatchActivity.class);
                                    adminCenter.putExtra("adminCenter", center);
                                    startActivity(adminCenter);
                                    //startActivity(new Intent(signinAdministrator.this, StaffViewVaccine.class));
                                    break;
                                }

                                if(Administrator == null){
                                    Toast.makeText(signinAdministrator.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                                }
                            }

                        });
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signinAdministrator.this, signup_healthcare.class));
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


package com.example.bmc208;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class signup_patient extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText fullname;
    EditText email;
    EditText staffid;
    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_patient);

        username = findViewById(R.id.enter_username);
        password = findViewById(R.id.enter_password);
        fullname = findViewById(R.id.enter_fullname);
        email = findViewById(R.id.enter_email);
        staffid = findViewById(R.id.enter_staffid);
        signin = findViewById(R.id.text_signup);
    }

    public void SignUpButtonClick(View view) {
    }
}
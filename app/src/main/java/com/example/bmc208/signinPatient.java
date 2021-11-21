package com.example.bmc208;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class signinPatient extends AppCompatActivity {

    TextView textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_patient);

        textViewSignUp = findViewById(R.id.text_signup);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signinPatient.this, signup.class));
            }
        });
    }
}
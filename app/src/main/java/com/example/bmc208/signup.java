package com.example.bmc208;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void patient_page(View view) {
        startActivity(new Intent(signup.this, signinPatient.class));
    }

    public void administrator_page(View view) {
        startActivity(new Intent(signup.this, signinAdministrator.class));
    }
}
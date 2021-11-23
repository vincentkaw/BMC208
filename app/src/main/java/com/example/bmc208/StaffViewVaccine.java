package com.example.bmc208;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StaffViewVaccine extends AppCompatActivity {

    Button pfizerButton;
    Button sinovacButton;
    Button astrazenecaButton;

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_view_vaccine);

        pfizerButton = findViewById(R.id.button_staff_pfizer_vaccine);
        sinovacButton = findViewById(R.id.button_staff_sino_vaccine);
        astrazenecaButton = findViewById(R.id.button_staff_astra_vaccine);

        Bundle extras = getIntent().getExtras();
        String center = extras.getString("adminCenter");
        Toast.makeText(StaffViewVaccine.this, center, Toast.LENGTH_SHORT).show();

        pfizerButton.setOnClickListener(view -> {
            Intent batchIDActivityIntent = new Intent(StaffViewVaccine.this, administrator_batch.class);
            startActivity(batchIDActivityIntent);
        });
        sinovacButton.setOnClickListener(view -> {
            Intent batchIDActivityIntent = new Intent(StaffViewVaccine.this, administrator_batch.class);
            startActivity(batchIDActivityIntent);
        });
        astrazenecaButton.setOnClickListener(view -> {
            Intent batchIDActivityIntent = new Intent(StaffViewVaccine.this, administrator_batch.class);
            startActivity(batchIDActivityIntent);
        });

        drawerLayout = findViewById(R.id.drawer_layout);
    }


    public void ClickMenu(View view){
        //Open drawer
        AddBatchActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        //Close drawer
        AddBatchActivity.closeDrawer(drawerLayout);
    }

    public void ClickAddBatch(View view){
        //Redirect activity to home
        //AddBatchActivity.redirectActivity(this, AddBatchActivity.class);
        Bundle extras = getIntent().getExtras();
        String center = extras.getString("adminCenter");
        Intent adminCenter = new Intent(StaffViewVaccine.this, AddBatchActivity.class);
        adminCenter.putExtra("adminCenter", center);
        startActivity(adminCenter);
    }

    public void ClickViewVaccine(View view){
        //Recreate activity
        recreate();
    }

    /*public void ClickAboutUs(View view){
        //Redirect activity to about us
        AddBatchActivity.redirectActivity(this, AboutUs.class);
    }*/

    public void ClickLogOut(View view){
        //Close app
        AddBatchActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        AddBatchActivity.closeDrawer(drawerLayout);
    }
}
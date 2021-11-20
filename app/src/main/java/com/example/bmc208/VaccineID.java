package com.example.bmc208;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class VaccineID extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_id);

        //Assign variable
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
        AddBatchActivity.redirectActivity(this, AddBatchActivity.class);
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
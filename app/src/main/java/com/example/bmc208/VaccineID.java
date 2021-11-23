package com.example.bmc208;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VaccineID extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_id);
        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_vaccine_id);

        ArrayList<Vaccine>vaccines = new ArrayList<Vaccine>();
        vaccines.add(new Vaccine("P0001", "12/12/12", "", "Pending"));
        vaccines.add(new Vaccine("P0001", "12/12/12", "", "Confirmed"));
        vaccines.add(new Vaccine("P0001", "12/12/12", "", "Administered"));

        VaccineAdapter adapter = new VaccineAdapter(vaccines);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(recyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


               /*final Dialog confirmDialog = new Dialog(VaccineID.this);
                final Dialog administeredDialog = new Dialog(VaccineID.this);

                confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                confirmDialog.setCancelable(true);
                confirmDialog.setContentView(R.layout.confirm_dialog);

                administeredDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                administeredDialog.setCancelable(true);
                administeredDialog.setContentView(R.layout.confirm_dialog);

                Button confirmButton = confirmDialog.findViewById(R.id.confirm_button);
                Button rejectButton = confirmDialog.findViewById(R.id.reject_button);
                Button administeredButton = administeredDialog.findViewById(R.id.administered_button);

                    confirmDialog.show();*/

                Intent intent = new Intent(VaccineID.this, ConfirmAdministeredActivity.class);
                //We have to pass key-value parameters
                intent.putExtra("Vaccine", vaccines.get(position));
                startActivity(intent);


            }
        }));

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
        AddBatchActivity.redirectActivity(this, StaffViewVaccine.class);
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
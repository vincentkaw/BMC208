package com.example.bmc208;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

public class administrator_batch extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_batch);

        drawerLayout = findViewById(R.id.drawer_layout);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_administrator_batch_id);

        BatchID[] batches = {
                new BatchID("P10000", "12/12/2021", "10","1"),
                new BatchID("P20000", "12/12/2021", "20","2"),
                new BatchID("P30000", "12/12/2021", "30","3")
        };

        BatchIDAdapter adapter = new BatchIDAdapter(batches);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new  RecyclerViewItemClickListener(recyclerView, new RecyclerViewItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {


                Intent intent = new Intent(administrator_batch.this, VaccineID.class);
                //We have to pass key-value parameters
                intent.putExtra("BatchID", batches[position]);
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

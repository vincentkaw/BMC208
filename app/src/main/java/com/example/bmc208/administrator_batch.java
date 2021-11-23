package com.example.bmc208;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class administrator_batch extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_batch);

        drawerLayout = findViewById(R.id.drawer_layout);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_administrator_batch_id);

        ArrayList<BatchID> batches = new ArrayList<BatchID>();
        batches.add(new BatchID("P000001", "12/12/12", "30", "1"));
        batches.add(new BatchID("P000001", "12/12/12", "20", "2"));
        batches.add(new BatchID("P000001", "12/12/12", "10", "3"));


        BatchIDAdapter adapter = new BatchIDAdapter(batches);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new  RecyclerViewItemClickListener(recyclerView, new RecyclerViewItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {


                Intent intent = new Intent(administrator_batch.this, VaccineID.class);
                //We have to pass key-value parameters
                intent.putExtra("BatchID", batches.get(position));
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

    public void ClickLogOut(View view){
        //Close app
        AddBatchActivity.redirectActivity(this, signup.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        AddBatchActivity.closeDrawer(drawerLayout);
    }
}

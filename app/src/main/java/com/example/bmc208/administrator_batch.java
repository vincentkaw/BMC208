package com.example.bmc208;

import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class administrator_batch extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private int Available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_batch);

        drawerLayout = findViewById(R.id.drawer_layout);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Bundle extras = getIntent().getExtras();
        String center = extras.getString("adminCenter");
        String vaccine = extras.getString("vaccine");
        String vaccineBatch = "";

        if (vaccine.equals("pfizer")){
            vaccineBatch = "PFIZER_BATCH";
        }else if (vaccine.equals("sino")){
            vaccineBatch = "SINO_BATCH";
        } else if (vaccine.equals("astra")){
            vaccineBatch = "ASTRA_BATCH";
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view_administrator_batch_id);

        ArrayList<BatchID> batches = new ArrayList<BatchID>();



        db.collection(vaccineBatch)
                .whereEqualTo("center", center)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("Appointment")
                                        .whereEqualTo("batchID", document.getString("batchID"))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    Available = 0;
                                                    for (QueryDocumentSnapshot pending : task.getResult()) {
                                                        if (!pending.getString("email").equals("")){
                                                            Available++;
                                                        }
                                                    }
                                                }
                                                batches.add(new BatchID(document.getString("batchID"), document.getString("date"), document.getString("quantity"), String.valueOf(Available)));
                                                BatchIDAdapter adapter = new BatchIDAdapter(batches);
                                                recyclerView.setAdapter(adapter);
                                            }
                                        });
                            }
                        }

                    }
                });

        //String.valueOf(Available)
        recyclerView.addOnItemTouchListener(new  RecyclerViewItemClickListener(recyclerView, new RecyclerViewItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {


                Intent intent = new Intent(administrator_batch.this, VaccineID.class);
                //We have to pass key-value parameters
                intent.putExtra("BatchID", batches.get(position));
                intent.putExtra("adminCenter", center);
                intent.putExtra("vaccine", vaccine);
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

package com.example.bmc208;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class administrator_batch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_batch);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_administrator_batch_id);

        BatchID[] batches = {
                new BatchID("P10000", "12/12/2021", 10,1)
        };

        BatchIDAdapter adapter = new BatchIDAdapter(batches);
        recyclerView.setAdapter(adapter);
    }
}

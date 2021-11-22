package com.example.bmc208;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BatchIDAdapter extends RecyclerView.Adapter<BatchIDAdapter.BatchIDViewHolder>{

    ArrayList<BatchID> batches;

    public BatchIDAdapter(ArrayList<BatchID> batches){
        this.batches = batches;
    }

    @NonNull
    @Override
    public BatchIDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.administrator_batch_id, parent, false);
        return new BatchIDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchIDViewHolder holder, int position) {
        holder.bind(batches.get(position));
    }

    @Override
    public int getItemCount() {
        return batches.size();
    }

    public static class  BatchIDViewHolder extends RecyclerView.ViewHolder{
        TextView viewBatchID;
        TextView viewExpiryStatus;
        TextView viewAvailableQuantity;
        TextView viewPendingQuantity;

        public BatchIDViewHolder(@NonNull View itemView) {
            super(itemView);

            viewBatchID = itemView.findViewById(R.id.text_view_batch_id);
            viewExpiryStatus = itemView.findViewById(R.id.text_view_status);
            viewAvailableQuantity = itemView.findViewById(R.id.text_view_available);
            viewPendingQuantity = itemView.findViewById(R.id.text_view_quantity);
        }

        public void bind(BatchID batchID){
            viewBatchID.setText(batchID.batchID);
            viewExpiryStatus.setText(batchID.expiryStatus);
            viewAvailableQuantity.setText(batchID.availableQuantity);
            viewPendingQuantity.setText(batchID.pendingQuantity);
        }
    }
}

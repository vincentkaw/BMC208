package com.example.bmc208;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PatientBatchIDAdapter extends RecyclerView.Adapter<PatientBatchIDAdapter.PatientBatchIdViewHolder>{

    ArrayList<PatientBatchID> patientBatches;

    public PatientBatchIDAdapter(ArrayList<PatientBatchID> batches){
        this.patientBatches = batches;
    }

    @NonNull
    @Override
    public PatientBatchIdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_batches, parent, false);
        return new PatientBatchIDAdapter.PatientBatchIdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientBatchIdViewHolder holder, int position) {
        holder.bind(patientBatches.get(position));
    }

    @Override
    public int getItemCount() {
        return patientBatches.size();
    }


    public static class PatientBatchIdViewHolder extends RecyclerView.ViewHolder{
        TextView viewBatchID;
        TextView viewExpiryDate;
        TextView viewQuantity;

        public PatientBatchIdViewHolder(@NonNull View itemView) {
            super(itemView);
            viewBatchID = itemView.findViewById(R.id.text_view_patient_batch_id);
            viewExpiryDate = itemView.findViewById(R.id.text_view_patient_expiry_date);
            viewQuantity = itemView.findViewById(R.id.text_view_patient_quantity);
        }

        public void bind(PatientBatchID patientBatchID){
            viewBatchID.setText(patientBatchID.batchID);
            viewExpiryDate.setText(patientBatchID.expiryStatus);
            viewQuantity.setText(patientBatchID.quantity);
        }
    }
}


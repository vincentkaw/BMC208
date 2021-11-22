package com.example.bmc208;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.VaccineViewHolder>{


    ArrayList<Vaccine>vaccines;


    public VaccineAdapter(ArrayList<Vaccine> vaccines){
        this.vaccines = vaccines;
    }

    @NonNull
    @Override
    public VaccineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_vaccine_batch_id, parent, false);




        return new VaccineAdapter.VaccineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccineViewHolder holder, int position) {
        holder.bind(vaccines.get(position));



    }

    @Override
    public int getItemCount() {
        return vaccines.size();
    }

    public static class VaccineViewHolder extends RecyclerView.ViewHolder{

        TextView viewVaccineID;
        TextView viewAppointmentDate;
        TextView viewRemarks;
        TextView viewStatus;

        public VaccineViewHolder(@NonNull View itemView) {
            super(itemView);


            viewVaccineID = itemView.findViewById(R.id.text_view_vaccine_id);
            viewAppointmentDate = itemView.findViewById(R.id.text_view_appointment_date);
            viewRemarks = itemView.findViewById(R.id.text_view_remarks);
            viewStatus = itemView.findViewById(R.id.text_view_status);
        }

        public void bind(Vaccine vaccine){
            viewVaccineID.setText(vaccine.vaccineID);
            viewAppointmentDate.setText(vaccine.appointmentDate);
            viewRemarks.setText(vaccine.remarks);
            viewStatus.setText(vaccine.status);
        }
    }
}

package com.example.bmc208;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.VaccineViewHolder>{


    ArrayList<Vaccine>vaccines;
    Dialog confirm;
    Dialog administered;

    public VaccineAdapter(ArrayList<Vaccine> vaccines){
        this.vaccines = vaccines;
    }

    @NonNull
    @Override
    public VaccineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_vaccine_batch_id, parent, false);
        VaccineViewHolder viewHolder = new VaccineViewHolder(view);

        confirm = new Dialog(parent.getContext());
        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm.setCancelable(true);
        confirm.setContentView(R.layout.confirm_dialog);

        administered = new Dialog(parent.getContext());
        administered.requestWindowFeature(Window.FEATURE_NO_TITLE);
        administered.setCancelable(true);
        administered.setContentView(R.layout.administered_dialog);

        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Collections.singleton(vaccines).contains("Pending")){
                    confirm.show();
                }else if (Collections.singleton(vaccines).contains("Confirmed")){
                    administered.show();
                }

            }
        });

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
        private CardView item;
        TextView viewVaccineID;
        TextView viewAppointmentDate;
        TextView viewRemarks;
        TextView viewStatus;

        public VaccineViewHolder(@NonNull View itemView) {
            super(itemView);

            item = (CardView) itemView.findViewById(R.id.vaccine_id_item);
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

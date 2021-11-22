package com.example.bmc208;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;


public class administrator_add_center extends AppCompatDialogFragment {

    private EditText editTextCenterName;
    private EditText editTextAddress;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_administrator_add_center, null);

        builder.setView(view)
                .setTitle("Add New Center")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AdministratorCenter administratorCenter = new AdministratorCenter();
                        administratorCenter.setCenterid(UUID.randomUUID().toString());
                        administratorCenter.setName(editTextCenterName.getText().toString());
                        administratorCenter.setAddress(editTextAddress.getText().toString());

                        db.collection(AdministratorCenter.COLLECTION_NAME)
                                .document()
                                .set(administratorCenter)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        signup_healthcare.AdministratorCenter = administratorCenter;
                                    }
                                });
                    }
                });

        editTextCenterName = view.findViewById(R.id.edit_centername);
        editTextAddress = view.findViewById(R.id.edit_address);
        return builder.create();
    }
}
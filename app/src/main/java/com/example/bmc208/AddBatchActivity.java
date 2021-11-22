package com.example.bmc208;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AddBatchActivity extends AppCompatActivity {
    //Initialize variable

    DrawerLayout drawerLayout;
    Spinner vaccineSpinner;
    TextView vaccineName;
    TextView manufacturerName;
    TableLayout addBatchTable;
    Button addBatchButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batch);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        vaccineSpinner = findViewById(R.id.vaccine_spinner);
        vaccineName = findViewById(R.id.vaccine_name_text_view);
        manufacturerName = findViewById(R.id.manufacturer_name_text_view);
        addBatchTable = findViewById(R.id.add_batch_table_layout);
        addBatchButton = findViewById(R.id.add_batch_button);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddBatchActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.vaccines));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vaccineSpinner.setAdapter(myAdapter);

        vaccineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        addBatchTable.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        addBatchTable.setVisibility(View.VISIBLE);
                        vaccineName.setText("Pfizer");
                        manufacturerName.setText("Pfizer Inc");
                        break;
                    case 2:
                        addBatchTable.setVisibility(View.VISIBLE);
                        vaccineName.setText("Sinovac");
                        manufacturerName.setText("Sinovac Biotech Ltd");
                        break;
                    case 3:
                        addBatchTable.setVisibility(View.VISIBLE);
                        vaccineName.setText("AstraZeneca");
                        manufacturerName.setText("AstraZeneca plc");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        
        addBatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showAddBatchDialog();
            }
        });

    }

    private void showAddBatchDialog() {
        final Dialog dialog = new Dialog( AddBatchActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_batch_dialog);

        final EditText batchNoEditText = dialog.findViewById(R.id.batch_no_edit_text);
        final EditText expiryDateEditText = dialog.findViewById(R.id.expiry_date_edit_text);
        final EditText quantityEditText = dialog.findViewById(R.id.quantity_edit_text);




        Button addButton = dialog.findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String batchNo = batchNoEditText.getText().toString();
                String expiryDate = expiryDateEditText.getText().toString();
                String quantity = quantityEditText.getText().toString();
                batchData(batchNo ,expiryDate,quantity);
                dialog.dismiss();

                if (vaccineName.getText().toString().equals("Pfizer")){
                    Pfizer_Batch batch = new Pfizer_Batch();
                    batch.setPfizerID(UUID.randomUUID().toString());
                    batch.setBatchID(batchNo);
                    batch.setCenter("Test Center");
                    batch.setDate(expiryDate);
                    batch.setQuantity(quantity);

                    db.collection(Pfizer_Batch.COLLECTION_NAME)
                            .document()
                            .set(batch)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AddBatchActivity.this, "Batch Successfully added", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddBatchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if (vaccineName.getText().toString().equals("Sinovac")){
                    Sino_Batch batch = new Sino_Batch();
                    batch.setSinoID(UUID.randomUUID().toString());
                    batch.setBatchID(batchNo);
                    batch.setCenter("Fake Center");
                    batch.setDate(expiryDate);
                    batch.setQuantity(quantity);

                    db.collection(Sino_Batch.COLLECTION_NAME)
                            .document()
                            .set(batch)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AddBatchActivity.this, "Batch Successfully added", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddBatchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if (vaccineName.getText().toString().equals("AstraZeneca")){
                    Astra_Batch batch = new Astra_Batch();
                    batch.setAstraID(UUID.randomUUID().toString());
                    batch.setBatchID(batchNo);
                    batch.setCenter("Fake Center");
                    batch.setDate(expiryDate);
                    batch.setQuantity(quantity);

                    db.collection(Astra_Batch.COLLECTION_NAME)
                            .document()
                            .set(batch)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AddBatchActivity.this, "Batch Successfully added", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddBatchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        dialog.show();

    }

    void batchData( String batchNo, String expiryDate, String quantity){

        if (batchNo.equals("")  || expiryDate.equals("") || quantity.equals("") ){
            Toast.makeText( AddBatchActivity.this, "There are information that is not fill.", Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText( AddBatchActivity.this, batchNo + " has been added", Toast.LENGTH_SHORT).show();
        }

    }

    public void ClickMenu(View view){
        //Open drawer
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //Close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //Close drawer layout
        //Check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            //When drawer is open
            //Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    
    public void ClickAddBatch(View view){
        //Recreate activity
        recreate();
    }
    
    public void ClickViewVaccine(View view){
        //Redirect activity to dashboard
        redirectActivity(this, StaffViewVaccine.class);
    }

    /*public void ClickAboutUs(View view){
        //Redirect activity to about us
        redirectActivity(this,);
    }*/
    public void ClickLogOut(View view){
        //Close app
        logout(this);
    }

    public static void logout(Activity activity) {
        //Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Set title
        builder.setTitle("Logout");
        //Set message
        builder.setMessage("Are you sure you want to logout?");
        //Positive yes button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Finish activity
                activity.finishAffinity();
                //Exit app
                System.exit(0);
            }
        });
        //Negative no button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();
            }
        });
        //Show dialog
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        //Initialize intent
        Intent intent = new Intent(activity, aClass);
        //Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Start activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        closeDrawer(drawerLayout);
    }


}
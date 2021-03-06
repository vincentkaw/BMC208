package com.example.bmc208;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.UUID;

public class AddBatchActivity extends AppCompatActivity {
    //Initialize variable

    DrawerLayout drawerLayout;
    Spinner vaccineSpinner;
    TextView vaccineName;
    TextView manufacturerName;
    TableLayout addBatchTable;
    Button addBatchButton;
    ConstraintLayout addBatchLayout;
    EditText batchNoEditText;
    EditText quantityEditText;
    TextView vaccineID;
    TextView expiryDateTextView;
    Button addButton;
    ImageView calendar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batch);

        Bundle extras = getIntent().getExtras();
        String center = extras.getString("adminCenter");

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        vaccineSpinner = findViewById(R.id.vaccine_spinner);
        vaccineName = findViewById(R.id.vaccine_name_text_view);
        manufacturerName = findViewById(R.id.manufacturer_name_text_view);
        addBatchTable = findViewById(R.id.add_batch_table_layout);
        addBatchButton = findViewById(R.id.add_batch_button);
        addBatchLayout = findViewById(R.id.add_batch_constraint_layout);
        batchNoEditText = findViewById(R.id.batch_no_edit_text);
        quantityEditText = findViewById(R.id.quantity_edit_text);
        vaccineID = findViewById(R.id.vaccine_id_text_view);
        expiryDateTextView = findViewById(R.id.date_text_view);
        addButton = findViewById(R.id.add_button);
        calendar = findViewById(R.id.expiry_date_Image_view);



        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddBatchActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.vaccines));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vaccineSpinner.setAdapter(myAdapter);

        vaccineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String vaccineNo = "";

                switch (i){
                    case 0:
                        addBatchLayout.setVisibility(View.INVISIBLE);
                        addBatchTable.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        addBatchTable.setVisibility(View.VISIBLE);
                        addBatchLayout.setVisibility(View.INVISIBLE);
                        vaccineID.setText("P000001");
                        vaccineName.setText("Pfizer");
                        manufacturerName.setText("Pfizer Inc");
                        break;
                    case 2:
                        addBatchTable.setVisibility(View.VISIBLE);
                        addBatchLayout.setVisibility(View.INVISIBLE);
                        vaccineName.setText("Sinovac");
                        vaccineID.setText("S000001");
                        manufacturerName.setText("Sinovac Biotech Ltd");
                        break;
                    case 3:
                        addBatchTable.setVisibility(View.VISIBLE);
                        addBatchLayout.setVisibility(View.INVISIBLE);
                        vaccineID.setText("A000001");
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
                addBatchLayout.setVisibility(View.VISIBLE);


            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DialogFragment datePicker = new DatePickerFragment();
                //datePicker.show(getSupportFragmentManager(), "date picker");
                datePicker();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addBatchLayout.setVisibility(View.INVISIBLE);

                String batchNo = batchNoEditText.getText().toString();
                String expiryDate = expiryDateTextView.getText().toString();
                String quantity = quantityEditText.getText().toString();
                batchData(batchNo ,expiryDate,quantity);

                boolean validate = validationAddBatch(batchNo, expiryDate ,quantity);

                if (vaccineName.getText().toString().equals("Pfizer") && validate == true){
                    Pfizer_Batch batch = new Pfizer_Batch();
                    batch.setPfizerID(UUID.randomUUID().toString());
                    batch.setBatchID(batchNo);
                    batch.setCenter(center);
                    batch.setDate(expiryDate);
                    batch.setQuantity(quantity);

                    db.collection(Pfizer_Batch.COLLECTION_NAME)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            String flag = "not same";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("batchID").equals(batchNoEditText.getText().toString())){
                                    flag = "same";
                                    Toast.makeText(AddBatchActivity.this, "This batch already exists", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            if (flag.equals("not same")){
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
                        }
                    });
                }
                else if (vaccineName.getText().toString().equals("Sinovac") && validate == true){
                    Sino_Batch batch = new Sino_Batch();
                    batch.setSinoID(UUID.randomUUID().toString());
                    batch.setBatchID(batchNo);
                    batch.setCenter(center);
                    batch.setDate(expiryDate);
                    batch.setQuantity(quantity);

                    db.collection(Sino_Batch.COLLECTION_NAME)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    String flag = "not same";
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getString("batchID").equals(batchNoEditText.getText().toString())){
                                            flag = "same";
                                            Toast.makeText(AddBatchActivity.this, "This batch already exists", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }
                                    if (flag.equals("not same")){
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
                                }
                            });


                }
                else if (vaccineName.getText().toString().equals("AstraZeneca") && validate == true){
                    Astra_Batch batch = new Astra_Batch();
                    batch.setAstraID(UUID.randomUUID().toString());
                    batch.setBatchID(batchNo);
                    batch.setCenter(center);
                    batch.setDate(expiryDate);
                    batch.setQuantity(quantity);

                    db.collection(Astra_Batch.COLLECTION_NAME)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    String flag = "not same";
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getString("batchID").equals(batchNoEditText.getText().toString())){
                                            flag = "same";
                                            Toast.makeText(AddBatchActivity.this, "This batch already exists", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }
                                    if (flag.equals("not same")){
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


                }
            }
        });



    }

    private boolean validationAddBatch(String batchNo, String expiryDate, String quantity) {

        if (batchNo.length() == 0) {
            batchNoEditText.requestFocus();
            batchNoEditText.setError("Batch No is not set");
            return false;
        } else if (expiryDate.equals("Date")) {
            expiryDateTextView.requestFocus();
            expiryDateTextView.setError("Date is not set");
            return false;
        } else if (quantity.length() == 0) {
            quantityEditText.requestFocus();
            quantityEditText.setError("Quantity is empty");
            return false;
        }else {
            return true;
        }

    }


    private void datePicker(){
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String dateString = day + "/" + (month+1) + "/" + year;
                expiryDateTextView.setText(dateString);
            }
        }, year, month, day);

        long now = System.currentTimeMillis();
        datePickerDialog.getDatePicker().setMinDate(now);
        datePickerDialog.show();
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
        Bundle extras = getIntent().getExtras();
        String center = extras.getString("adminCenter");
        Intent intent = new Intent(AddBatchActivity.this, StaffViewVaccine.class);
        intent.putExtra("adminCenter", center);
        startActivity(intent);
        //redirectActivity(this, StaffViewVaccine.class);
    }


    public void ClickLogOut(View view){
        //Close app
        redirectActivity(this, signup.class);
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
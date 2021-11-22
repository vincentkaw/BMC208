package com.example.bmc208;

public class PatientBatchID {
    String batchID;
    String expiryStatus;
    String quantity;

    public PatientBatchID(String batchID, String expiryStatus, String quantity){
        this.batchID = batchID;
        this.expiryStatus = expiryStatus;
        this.quantity = quantity;
    }
}

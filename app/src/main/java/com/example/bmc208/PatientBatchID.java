package com.example.bmc208;

public class PatientBatchID {
    String batchID;
    String expiryStatus;
    int quantity;

    public PatientBatchID(String batchID, String expiryStatus, int quantity){
        this.batchID = batchID;
        this.expiryStatus = expiryStatus;
        this.quantity = quantity;
    }
}

package com.example.bmc208;

import java.io.Serializable;

public class PatientBatchID implements Serializable {
    String batchID;
    String expiryStatus;
    String quantity;

    public PatientBatchID(String batchID, String expiryStatus, String quantity){
        this.batchID = batchID;
        this.expiryStatus = expiryStatus;
        this.quantity = quantity;
    }
}

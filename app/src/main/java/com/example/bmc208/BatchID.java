package com.example.bmc208;

import java.io.Serializable;

public class BatchID implements Serializable {
    String batchID;
    String expiryStatus;
    String availableQuantity;
    String pendingQuantity;

    public BatchID(String batchID, String expiryStatus, String availableQuantity, String pendingQuantity){
        this.batchID = batchID;
        this.expiryStatus = expiryStatus;
        this. availableQuantity = availableQuantity;
        this.pendingQuantity = pendingQuantity;
    }
}

package com.example.bmc208;

import java.io.Serializable;

public class BatchID {
    String batchID;
    String expiryStatus;
    int availableQuantity;
    int pendingQuantity;

    public BatchID(String batchID, String expiryStatus, int availableQuantity, int pendingQuantity){
        this.batchID = batchID;
        this.expiryStatus = expiryStatus;
        this. availableQuantity = availableQuantity;
        this.pendingQuantity = pendingQuantity;
    }
}

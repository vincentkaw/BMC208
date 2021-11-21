package com.example.bmc208;

import java.util.Date;

public class Astra_Batch {
    static String COLLECTION_NAME = "ASTRA_BATCH";
    private String batchID;
    private String center;
    private String date;
    private String quantity;

    public String getAstraID() {
        return astraID;
    }

    public void setAstraID(String astraID) {
        this.astraID = astraID;
    }

    private String astraID;

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

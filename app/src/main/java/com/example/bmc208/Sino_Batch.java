package com.example.bmc208;

import java.util.Date;

public class Sino_Batch {
    static String COLLECTION_NAME = "SINO_BATCH";
    private String batchID;
    private String center;
    private String date;
    private String quantity;
    private String sinoID;

    public String getSinoID() {
        return sinoID;
    }

    public void setSinoID(String sinoID) {
        this.sinoID = sinoID;
    }

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

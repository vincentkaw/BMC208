package com.example.bmc208;

import java.io.Serializable;

public class Vaccine implements Serializable {
    String vaccineID;
    String appointmentDate;
    String remarks;
    String status;

    public Vaccine(String vaccineID, String appointmentDate, String remarks, String status){
        this.vaccineID = vaccineID;
        this.appointmentDate = appointmentDate;
        this.remarks = remarks;
        this.status = status;
    }
}

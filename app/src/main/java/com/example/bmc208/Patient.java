package com.example.bmc208;

public class Patient {

    static String COLLECTION_NAME = "Patient";

    private String patientid;
    private String username;
    private String password;
    private String email;
    private String ic_passport;

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIc_passport() {
        return ic_passport;
    }

    public void setIc_passport(String ic_passport) {
        this.ic_passport = ic_passport;
    }
}

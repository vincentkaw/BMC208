package com.example.bmc208;

public class AdministratorCenter {

    static String COLLECTION_NAME = "AdministratorCenter";

    private String centerid;
    private String name;
    private String address;

    public String getCenterid() {
        return centerid;
    }

    public void setCenterid(String centerid) {
        this.centerid = centerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

package com.example.bmc208;

public class Administrator {

    static String COLLECTION_NAME = "Administrator";

    private String administratorid;
    private String centername;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String staffid;

    public String getAdministratorid() {
        return administratorid;
    }

    public void setAdministratorid(String administratorid) { this.administratorid = administratorid; }

    public String getCentername() { return centername; }

    public void setCentername(String centername) { this.centername = centername; }

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }
}

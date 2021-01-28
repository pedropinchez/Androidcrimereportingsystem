package com.example.androidcrimereportingsystem;

public class MyListData {

    private String username;
    private String userimage;
    private String type;
    private String date;
    private String time;
    private String location;
    private String crime;
    private String details;

    public MyListData(){}

    public MyListData(String username, String type, String date, String time,String location,String crime, String details, String userimage) {
        this.username = username;
        this.type = type;
        this.date = date;
        this.time = time;
        this.location=location;
        this.crime=crime;
        this.details=details;
        this.userimage=userimage;


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location =location;
    }

    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime =crime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details =details;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage =userimage;
    }
}


package com.example.hospitalregistry.custom;

public class RegistrationElement {
    private String doctor;
    private String date;
    private String time;

    public RegistrationElement(String doctor, String  date, String time){
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
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
}

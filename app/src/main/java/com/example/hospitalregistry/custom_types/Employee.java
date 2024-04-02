package com.example.hospitalregistry.custom_types;

public class Employee {
    private String departament;
    private String fullname;
    public Employee(){
        this.departament = "";
        this.fullname = "";
    }
    public Employee(String departament, String fullname){
        this.fullname = fullname;
        this.departament = departament;
    }

    public String getDepartament() {
        return departament;
    }

    public String getFullname() {
        return fullname;
    }

}

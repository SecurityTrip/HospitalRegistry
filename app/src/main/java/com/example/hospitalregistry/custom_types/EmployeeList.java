package com.example.hospitalregistry.custom_types;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList {
    private List<Employee> list;

    public EmployeeList() {
        this.list = new ArrayList<>();
    }

    public void add(Employee employee) {
        list.add(employee);
    }

    public void remove(Employee employee) {
        list.remove(employee);
    }

    public Employee get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }
}


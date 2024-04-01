package com.example.hospitalregistry;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueueFragment extends Fragment {
    EmployeeList  EmployeeList;
    String[] departaments, doctors;
    AutoCompleteTextView depatamentsDropdown, doctorsDropdown;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean isDepartamentSelected, isDoctorSelected = false;


    public QueueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_queue, container, false);

        EmployeeList = new EmployeeList();
        depatamentsDropdown = (AutoCompleteTextView) RootView.findViewById(R.id.DepartamentsDropdown);
        doctorsDropdown = (AutoCompleteTextView) RootView.findViewById(R.id.DoctorsDropdown);

        db.collection("employees")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (isAdded() && task.isSuccessful()) {
                            Set<String> uniqueDepartments = new HashSet<>();
                            Set<String> uniqueDoctors = new HashSet<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Employee tmp = new Employee((String) document.get("departament"), (String) document.get("fullname"));
                                EmployeeList.add(tmp);
                                uniqueDepartments.add(tmp.getDepartament());
                                uniqueDoctors.add(tmp.getFullname());
                            }

                            departaments = uniqueDepartments.toArray(new String[0]);
                            doctors = uniqueDoctors.toArray(new String[0]);

                            ArrayAdapter<String> departamentsList = new ArrayAdapter<String>(requireActivity(), R.layout.list_item, departaments);
                            ArrayAdapter<String> doctorsList = new ArrayAdapter<String>(requireActivity(), R.layout.list_item, doctors);

                            depatamentsDropdown.setAdapter(departamentsList);
                            doctorsDropdown.setAdapter(doctorsList);
                        }
                    }
                });

        depatamentsDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = departaments[position];
                isDepartamentSelected = true;
                // Действия при выборе элемента из списка отделений
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Действия при отсутствии выбора
            }
        });

        doctorsDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = doctors[position];
                isDoctorSelected = true;
                // Действия при выборе элемента из списка врачей
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Действия при отсутствии выбора
            }
        });


        return RootView;
    }

}
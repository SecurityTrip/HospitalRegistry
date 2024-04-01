package com.example.hospitalregistry.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.compose.runtime.Composable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.hospitalregistry.Employee;
import com.example.hospitalregistry.EmployeeList;
import com.example.hospitalregistry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class QueueFragment extends Fragment {
    EmployeeList employeeList;
    String[] departaments, doctors;
    AutoCompleteTextView depatamentsDropdown, doctorsDropdown;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean isDepartamentSelected, isDoctorSelected = false;
    Button regBtn;


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

        employeeList = new EmployeeList();
        depatamentsDropdown = (AutoCompleteTextView) RootView.findViewById(R.id.DepartamentsDropdown);
        doctorsDropdown = (AutoCompleteTextView) RootView.findViewById(R.id.DoctorsDropdown);
        regBtn = (Button) RootView.findViewById(R.id.registerButton);

        db.collection("employees")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (isAdded() && getContext() != null && task.isSuccessful()) {
                            Set<String> uniqueDepartments = new HashSet<>();
                            Set<String> uniqueDoctors = new HashSet<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Employee tmp = new Employee((String) document.get("departament"), (String) document.get("fullname"));
                                employeeList.add(tmp);
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

        depatamentsDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = departaments[position];
                Log.d("QueueFragment in depatamentsDropdown", "Selected item: " + selectedItem);
                isDepartamentSelected = true;
                List<String> doctorsArr = new ArrayList<String>();
                for (int i = 0; i < employeeList.size(); i++){
                    if(Objects.equals(employeeList.get(i).getDepartament(), selectedItem)){
                        doctorsArr.add(employeeList.get(i).getFullname());
                    }
                }
                String[] doctorsNew = doctorsArr.toArray(new String[0]);
                ArrayAdapter<String> doctorsList = new ArrayAdapter<String>(requireActivity(), R.layout.list_item, doctorsNew);
                doctorsDropdown.setAdapter(doctorsList);
            }
        });

        doctorsDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = doctors[position];
                isDoctorSelected = true;
                Log.d("QueueFragment in doctorsDropdown", "Selected item: " + selectedItem);
                List<String> depatamentsArr = new ArrayList<String>();
                for (int i = 0; i < employeeList.size(); i++){
                    if(Objects.equals(employeeList.get(i).getFullname(), selectedItem)){
                        depatamentsArr.add(employeeList.get(i).getDepartament());
                        break;
                    }
                }
                String[] depatamentsNew = depatamentsArr.toArray(new String[0]);
                ArrayAdapter<String> doctorsList = new ArrayAdapter<String>(requireActivity(), R.layout.list_item, depatamentsNew);
                depatamentsDropdown.setAdapter(doctorsList);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDepartamentSelected && isDoctorSelected){
                    Log.d("RegistateButton", "Doctor and Departament Selected");
                }else {
                    Log.d("RegistateButton", "Doctor or Departament is not Selected");
                }
            }
        });

        return RootView;
    }

}
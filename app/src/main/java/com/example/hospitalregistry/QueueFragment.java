package com.example.hospitalregistry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


public class QueueFragment extends Fragment {

    String[] departaments = {"Cardiology", "Surgency"};
    ArrayAdapter<String> departamentsList;
    AutoCompleteTextView depatamentsDropdown;
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

        depatamentsDropdown = (AutoCompleteTextView)RootView.findViewById(R.id.DepartamentsDropdown);
        departamentsList = new ArrayAdapter<String>(getActivity(), R.layout.list_item, departaments);

        depatamentsDropdown.setAdapter(departamentsList);

        depatamentsDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        // Inflate the layout for this fragment
        return RootView;
    }
}
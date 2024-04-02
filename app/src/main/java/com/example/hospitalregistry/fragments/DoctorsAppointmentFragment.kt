package com.example.hospitalregistry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.hospitalregistry.custom_types.Employee
import com.example.hospitalregistry.custom_types.EmployeeList
import com.example.hospitalregistry.R
import com.google.firebase.firestore.FirebaseFirestore

class DoctorsAppointmentFragment : Fragment() {
    var employeeList: EmployeeList? = null
    lateinit var departaments: Array<String>
    lateinit var doctors: Array<String>
    var depatamentsDropdown: AutoCompleteTextView? = null
    var doctorsDropdown: AutoCompleteTextView? = null
    var db = FirebaseFirestore.getInstance()
    var isDepartamentSelected = false
    var isDoctorSelected = false
    var regBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val RootView = inflater.inflate(R.layout.fragment_doctors_appointment, container, false)
        employeeList = EmployeeList()
        depatamentsDropdown =
            RootView.findViewById<View>(R.id.DepartamentsDropdown) as AutoCompleteTextView
        doctorsDropdown = RootView.findViewById<View>(R.id.DoctorsDropdown) as AutoCompleteTextView
        regBtn = RootView.findViewById<View>(R.id.registerButton) as Button
        db.collection("employees")
            .get()
            .addOnCompleteListener { task ->
                if (isAdded && context != null && task.isSuccessful) {
                    val uniqueDepartments: MutableSet<String> =
                        HashSet()
                    val uniqueDoctors: MutableSet<String> =
                        HashSet()
                    for (document in task.result) {
                        val tmp =
                            Employee(
                                document["departament"] as String?,
                                document["fullname"] as String?
                            )
                        employeeList!!.add(tmp)
                        uniqueDepartments.add(tmp.departament)
                        uniqueDoctors.add(tmp.fullname)
                    }
                    departaments = uniqueDepartments.toTypedArray<String>()
                    doctors = uniqueDoctors.toTypedArray<String>()
                    val departamentsList =
                        ArrayAdapter(requireActivity(), R.layout.list_item, departaments)
                    val doctorsList =
                        ArrayAdapter(requireActivity(), R.layout.list_item, doctors)
                    depatamentsDropdown!!.setAdapter(departamentsList)
                    doctorsDropdown!!.setAdapter(doctorsList)
                }
            }
        depatamentsDropdown!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val selectedItem = departaments[position]
                Log.d(
                    "QueueFragment in depatamentsDropdown",
                    "Selected item: $selectedItem"
                )
                isDepartamentSelected = true
                val doctorsArr: MutableList<String> = ArrayList()
                for (i in 0 until employeeList!!.size()) {
                    if (employeeList!![i].departament == selectedItem) {
                        doctorsArr.add(employeeList!![i].fullname)
                    }
                }
                val doctorsNew = doctorsArr.toTypedArray<String>()
                val doctorsList = ArrayAdapter(requireActivity(), R.layout.list_item, doctorsNew)
                doctorsDropdown!!.setAdapter(doctorsList)
            }
        doctorsDropdown!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val selectedItem = doctors[position]
                isDoctorSelected = true
                Log.d(
                    "QueueFragment in doctorsDropdown",
                    "Selected item: $selectedItem"
                )
                val depatamentsArr: MutableList<String> = ArrayList()
                for (i in 0 until employeeList!!.size()) {
                    if (employeeList!![i].fullname == selectedItem) {
                        depatamentsArr.add(employeeList!![i].departament)
                        break
                    }
                }
                val depatamentsNew = depatamentsArr.toTypedArray<String>()
                val doctorsList =
                    ArrayAdapter(requireActivity(), R.layout.list_item, depatamentsNew)
                depatamentsDropdown!!.setAdapter(doctorsList)
            }
        regBtn!!.setOnClickListener {
            if (isDepartamentSelected && isDoctorSelected) {
                Log.d("RegistateButton", "Doctor and Departament Selected")
            } else {
                Log.d("RegistateButton", "Doctor or Departament is not Selected")
            }
        }
        return RootView
    }
}
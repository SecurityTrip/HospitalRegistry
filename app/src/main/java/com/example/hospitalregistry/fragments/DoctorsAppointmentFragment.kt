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
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hospitalregistry.custom.DateTimePickerFragment
import com.example.hospitalregistry.custom.Employee
import com.example.hospitalregistry.custom.EmployeeList
import com.example.hospitalregistry.custom.Transliter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class DoctorsAppointmentFragment : Fragment(), DateTimePickerFragment.OnDateTimeSetListener {
    private var employeeList: EmployeeList? = null
    lateinit var departaments: Array<String>
    lateinit var doctors: Array<String>
    private var depatamentsDropdown: AutoCompleteTextView? = null
    private var doctorsDropdown: AutoCompleteTextView? = null
    private var db = FirebaseFirestore.getInstance()
    private var isDepartamentSelected = false
    private var isDoctorSelected = false
    private var regBtn: Button? = null
    private var backBtn: ImageButton? = null
    private var selectedDoctor: String? = null
    private var c_year: Int = 0
    private var c_month: Int = 0
    private var c_day: Int = 0
    private var c_hourOfDay: Int = 0
    private var c_minute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDateTimeSet(year: Int, month: Int, day: Int, hourOfDay: Int, minute: Int) {
        c_year = year
        c_month = month
        c_day = day
        c_hourOfDay = hourOfDay
        c_minute = minute
        makeRegistation(c_year, c_month, c_day, c_hourOfDay, c_minute, )

    }

    private fun makeRegistation(year: Int, month: Int, day: Int, hourOfDay: Int, minute: Int) {
        val date = "$day.$month.$year"
        val time = "$hourOfDay:$minute"
        val id = FirebaseAuth.getInstance().currentUser?.uid

        val data = hashMapOf(
            "date" to date,
            "time" to time,
            "doctor" to selectedDoctor,
            "id" to id,
        )

        db.collection("registrations")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d("Registration", "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Registration", "Error adding document", e)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val RootView = inflater.inflate(com.example.hospitalregistry.R.layout.fragment_doctors_appointment, container, false)
        employeeList = EmployeeList()
        depatamentsDropdown =
            RootView.findViewById<View>(com.example.hospitalregistry.R.id.DepartamentsDropdown) as AutoCompleteTextView
        doctorsDropdown = RootView.findViewById<View>(com.example.hospitalregistry.R.id.DoctorsDropdown) as AutoCompleteTextView
        regBtn = RootView.findViewById<View>(com.example.hospitalregistry.R.id.registerButton) as Button
        backBtn = RootView.findViewById<View>(com.example.hospitalregistry.R.id.backButton) as ImageButton

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
                    departaments = Transliter.translateArr(departaments)

                    doctors = uniqueDoctors.toTypedArray<String>()
                    doctors = Transliter.translateArr(doctors)

                    departaments.sort()
                    doctors.sort()


                    val departamentsList =
                        ArrayAdapter(requireActivity(), com.example.hospitalregistry.R.layout.list_item, departaments)
                    val doctorsList =
                        ArrayAdapter(requireActivity(), com.example.hospitalregistry.R.layout.list_item, doctors)
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
                var doctorsNew = doctorsArr.toTypedArray<String>()
                val anyDoctor: String = activity?.getString(com.example.hospitalregistry.R.string.any_doctor) ?: ""
                doctorsNew = arrayOf(anyDoctor) + doctorsNew
                val doctorsList = ArrayAdapter(requireActivity(), com.example.hospitalregistry.R.layout.list_item, doctorsNew)
                doctorsDropdown!!.setAdapter(doctorsList)
            }

        doctorsDropdown!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                selectedDoctor = doctors[position]
                isDoctorSelected = true
                Log.d(
                    "QueueFragment in doctorsDropdown",
                    "Selected item: $selectedDoctor"
                )
                val selectDateButton: Button = RootView.findViewById(com.example.hospitalregistry.R.id.date_time_btn)
                selectDateButton.visibility = View.VISIBLE;


            }

        val selectDateButton: Button = RootView.findViewById(com.example.hospitalregistry.R.id.date_time_btn)

        selectDateButton.setOnClickListener {
            Log.d("Call", "Date Picker called")
            val datePickerFragment = DateTimePickerFragment()
            datePickerFragment.setOnDateTimeSetListener(this)
            datePickerFragment.show(childFragmentManager, "DatePicker")
        }

        regBtn!!.setOnClickListener {
            if(!isDepartamentSelected && !isDoctorSelected){
                Log.d("RegistateButton", "Departament and Doctor are not selected")
                val toastMessage: String = activity?.getString(com.example.hospitalregistry.R.string.departament_and_doctor_are_not_selected) ?: ""
                Toast.makeText(
                    activity, toastMessage,
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isDoctorSelected) {
                Log.d("RegistateButton", "Doctor is not selected")
                val toastMessage: String = activity?.getString(com.example.hospitalregistry.R.string.doctor_not_selected) ?: ""
                Toast.makeText(
                    activity, toastMessage,
                    Toast.LENGTH_SHORT
                ).show()

            }
            //TODO datetime selector
            if(isDoctorSelected){

                Toast.makeText(
                    activity, "Departament and Doctor are selected",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
        backBtn!!.setOnClickListener {
            replaceFragment(HomeFragment())
        }

        return RootView
    }
    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(com.example.hospitalregistry.R.id.frame_layout, fragment)
            .commit()
    }
}
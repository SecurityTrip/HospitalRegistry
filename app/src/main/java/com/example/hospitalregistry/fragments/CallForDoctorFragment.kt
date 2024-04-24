package com.example.hospitalregistry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CallForDoctorFragment : Fragment() {
    private var db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                CallForDoctorScreen()
            }
        }
    }

    @Composable
    fun CallForDoctorScreen() {
        val fullName = remember { mutableStateOf("") }
        val description = remember { mutableStateOf("") }
        val address = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp)
                .padding(7.dp)
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
            ) {
                BackButton(onBackPressed = {
                    replaceFragment(HomeFragment())
                })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(15.dp))
                    .padding(16.dp)
            ) {
                Column {
                    OutlinedTextField(
                        value = fullName.value,
                        onValueChange = { fullName.value = it },
                        label = { Text("ФИО") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = description.value,
                        onValueChange = { description.value = it },
                        label = { Text("Опишите вашу проблему") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = address.value,
                        onValueChange = { address.value = it },
                        label = { Text("Адрес") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if(fullName.value.isNotEmpty() && description.value.isNotEmpty() && address.value.isNotEmpty()) {
                                    val id = FirebaseAuth.getInstance().currentUser?.uid

                                    val data = hashMapOf(
                                        "name" to fullName.value,
                                        "description" to description.value,
                                        "address" to address.value,
                                        "id" to id,
                                    )

                                    db.collection("orders")
                                        .add(data)
                                        .addOnSuccessListener { documentReference ->
                                            Log.d(
                                                "Registration",
                                                "DocumentSnapshot written with ID: ${documentReference.id}"
                                            )
                                            Toast.makeText(
                                                activity, "Успешно, ожидайте звонка", Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                        .addOnFailureListener { e ->
                                            Log.w("Registration", "Error adding document", e)
                                            Toast.makeText(
                                                activity, "Произошла ошибка, попробуйте позже", Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Вызвать")
                    }

                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(com.example.hospitalregistry.R.id.frame_layout, fragment)
            .commit()
    }
}
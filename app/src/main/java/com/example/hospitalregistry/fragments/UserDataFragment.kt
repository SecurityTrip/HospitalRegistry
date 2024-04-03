package com.example.hospitalregistry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.hospitalregistry.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class UserDataFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                Box(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxSize()
                        .background(Color.White)
                ){
                    Column {
                        var showUsernameDialog by remember { mutableStateOf(false) }
                        var showPasswordDialog by remember { mutableStateOf(false) }
                        var showEmailDialog by remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                        ) {
                            BackButton(onBackPressed = {
                                replaceFragment(SettingsFragment())
                            })

                        }
                        Spacer(modifier = Modifier)
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    Color.LightGray,
                                    shape = RoundedCornerShape(15.dp)
                                )
                        ) {
                            Column {
                                Box {
                                    val username = resources.getString(R.string.name)
                                    ListElement(title = username){
                                        showUsernameDialog = true
                                    }
                                    ChangeUserData(
                                        showDialog = showUsernameDialog,
                                        title = resources.getString(R.string.new_name),
                                        buttonText = resources.getString(R.string.apply),
                                        onDismiss = { showUsernameDialog = false },
                                        onDataReturned = { text, success ->
                                            if (success) {
                                                changeUserData(name = text,password = "", email = "")
                                            }
                                        },
                                        onClose = {showUsernameDialog = it},

                                    )
                                }
                                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                                Box {
                                    val password = resources.getString(R.string.password)
                                    ListElement(title = password){
                                        showPasswordDialog = true
                                    }
                                    ChangeUserData(
                                        showDialog = showPasswordDialog,
                                        title = resources.getString(R.string.new_password),
                                        buttonText = resources.getString(R.string.apply),
                                        onDismiss = { showPasswordDialog = false },
                                        onDataReturned = { text, success ->
                                            if (success) {
                                                changeUserData(name = "",password = text, email = "")
                                            }
                                        },
                                        onClose = {showPasswordDialog = it}
                                    )
                                }
                                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                                Box {
                                    val email = resources.getString(R.string.Email)
                                    ListElement(title = email){
                                        showEmailDialog = true
                                    }
                                    ChangeUserData(
                                        showDialog = showEmailDialog,
                                        title = resources.getString(R.string.new_Email),
                                        buttonText = resources.getString(R.string.apply),
                                        onDismiss = { showEmailDialog = false },
                                        onDataReturned = { text, success ->
                                            if (success) {
                                                changeUserData(name = "",password = "", email = text)
                                            }
                                        },
                                        onClose = {showEmailDialog = it}
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    private fun changeUserData(name: String, password: String, email: String) {
        Log.d("Profile updater", "changeUserData function called")
        val user = Firebase.auth.currentUser


        if (name.isNotEmpty()){

            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }

            user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Profile updater", "User name updated.")
                }else{
                    Log.d("Profile updater", "Update user name failed.")
                }
            }

        }
        if (password.isNotEmpty()){
            user!!.updatePassword(password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Profile updater", "User password updated.")
                    }else{
                        Log.d("Profile updater", "Update user password failed.")
                    }
                }

        }
        if (email.isNotEmpty()){
            user!!.updateEmail("user@example.com")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Profile updater", "User email address updated.")
                    }else{
                        Log.d("Profile updater", "Update user email failed.")
                    }
                }
        }
    }
}

@Composable
fun ChangeUserData(
    title: String,
    buttonText: String,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDataReturned: (String, Boolean) -> Unit,
    onClose: (Boolean) -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ){

                    Text(title)
                }
            },
            modifier = Modifier
                .width(220.dp)
                .clip(
                    shape = RoundedCornerShape(15.dp)
                )
            ,
            buttons = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    var text: String by remember { mutableStateOf("") }

                    Column(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 20.dp)
                                .padding(10.dp),
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 16.sp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                        Button(
                            onClick = {
                                onDataReturned(text, true)
                                onClose(false)
                            }, // Вызовем функцию обратного вызова с текущим значением текста
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                        ) {
                            Text(text = buttonText)
                        }
                    }
                }
            }
        )
    }
}
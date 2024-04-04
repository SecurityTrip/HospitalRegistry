package com.example.hospitalregistry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.hospitalregistry.R
import com.google.accompanist.coil.rememberCoilPainter
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import com.example.hospitalregistry.SettingsActivity

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get the current user's display name from FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser
        val displayName = currentUser?.displayName ?: "Anonymous"

        // URL of your avatar
        val avatarUrl = "https://tommystinctures.com/wp-content/uploads/2020/10/avatar-icon-placeholder-1577909.jpg"


        return ComposeView(requireContext()).apply {
            setContent {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp)
                        .padding(7.dp)
                        .background(Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .height(185.dp)
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                                    .clip(
                                        shape = RoundedCornerShape(15.dp),

                                        )
                                    .border(
                                        1.dp,
                                        Color.LightGray,
                                        shape = RoundedCornerShape(15.dp)
                                    )
                            ){
                                val painter = rememberCoilPainter(request = avatarUrl)
                                ProfileCard(
                                    image = painter,
                                    name = displayName,
                                    description = "",
                                    onEditClick = { /*TODO*/ })

                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp)) // Пространство между Box 1 и Box 2
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                shape = RoundedCornerShape(15.dp),

                                )
                            .border(
                                1.dp,
                                Color.LightGray,
                                shape = RoundedCornerShape(15.dp)
                            )
                            //.background(color = Color.Red)

                    ) {
                        Column {
                            ListElement(
                                title = resources.getString(R.string.settingsTextField).toString(),
                                modifier = Modifier
                                    .background(Color.White)
                            ){
                                // Создание Intent для перехода к другой активности
                                val intent = Intent(activity, SettingsActivity::class.java)
                                // Вызов активности по созданному Intent
                                startActivity(intent)
                            }
                            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

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
    
}


@Composable
fun ProfileCard(
    image: Painter,
    name: String,
    description: String,
    onEditClick: () -> Unit,
    shape: Shape = MaterialTheme.shapes.medium
) {
    Card(
        shape = shape,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterVertically)
                    .clip(
                        shape = RoundedCornerShape(25.dp)
                    )
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(text = name)
                Text(text = description)
            }
        }
    }
}
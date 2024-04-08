package com.example.hospitalregistry.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AlertDialog
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.hospitalregistry.R
import java.util.Locale
import android.content.Intent
import com.example.hospitalregistry.MainActivity


class SettingsFragment : Fragment() {
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
                        Box(
                            modifier = Modifier
                        ) {
                            BackButton(onBackPressed = {
                                // Создание Intent для перехода к другой активности
                                val intent = Intent(activity, MainActivity::class.java)
                                intent.putExtra("source", "SettingsActivity");
                                // Вызов активности по созданному Intent
                                startActivity(intent)
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
                                var languageCode: String = Locale.getDefault().language
                                var languageName = Locale(languageCode).getDisplayLanguage(Locale(languageCode))
                                languageName = languageName.replaceFirstChar { it.uppercase() }

                                Log.d("Language","Language code: $languageCode, Language name: $languageName")
                                TextsInBox(leftText = resources.getString(R.string.language), rightText = languageName){
                                    Toast.makeText(context, resources.getString(R.string.language_change), Toast.LENGTH_SHORT).show()
                                }
                                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)


                                TextsInBox(leftText = resources.getString(R.string.user_data), rightText = ""){
                                    replaceFragment(UserDataFragment())
                                }
                                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
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

}

@Composable
fun TextsInBox(
    leftText: String,
    rightText: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(Color.White)
            ,
            contentAlignment = Alignment.Center
        ) {
            LeftText(leftText)
            RightText(rightText)
        }
    }

}

@Composable
fun BoxScope.LeftText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .align(Alignment.CenterStart)
            .padding(horizontal = 15.dp)
        ,
        style = TextStyle(fontSize = 16.sp, color = Color.Black),
        maxLines = 1
    )
}

@Composable
fun BoxScope.RightText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(horizontal = 20.dp)
        ,
        style = TextStyle(fontSize = 16.sp, color = Color.LightGray),
        maxLines = 1
    )
}

@Composable
fun BackButton(onBackPressed: () -> Unit) {
    IconButton(
        onClick = { onBackPressed() },
        modifier = Modifier.padding(8.dp),
        content = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colors.primary
            )
        }
    )
}

@Composable
fun ThemeSelectionDialog(
    dark: String,
    light: String,
    showDialog: Boolean,
    choseTheme: String,
    onDismiss: () -> Unit,
    onThemeSelected: (Boolean) -> Unit // Функция для передачи выбранной темы (true - светлая, false - темная)
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Text(choseTheme)
                }
            },
            modifier = Modifier
                .width(200.dp)
            ,
            buttons = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 8.dp),
                    ) {
                        ThemeOptionButton( light, true, onThemeSelected)
                        Spacer(modifier = Modifier.width(16.dp))
                        ThemeOptionButton( dark, false, onThemeSelected)
                    }
                }

            }
        )
    }
}

@Composable
fun ThemeOptionButton(
    text: String,
    isLightTheme: Boolean,
    onThemeSelected: (Boolean) -> Unit
) {
    Button(onClick = { onThemeSelected(isLightTheme) }) {
        Text(text = text)
    }
}
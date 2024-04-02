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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.hospitalregistry.R

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val painter1 = painterResource(id = R.drawable.registry)
                val title1 = stringResource(id = R.string.registration_office)
                val painter2 = painterResource(id = R.drawable.call_for_doctor)
                val title2 = stringResource(id = R.string.call_for_doctor)
                val titles: List<String> = resources.getStringArray(R.array.my_titles).toList()

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
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Плитка 1
                            Tile(title = title1, painter = painter1) {
                                Thread.sleep(150)
                                replaceFragment(DoctorsAppointmentFragment())
                            }

                            // Плитка 2
                            Tile(title = title2, painter = painter2) {
                                Thread.sleep(150)
                                replaceFragment(CallForDoctorFragment())
                            }
                        }
                    }
                    Spacer(modifier = Modifier) // Пространство между Box 1 и Box 2
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
                            //.background(Color.Red)

                    ) {
                        Column {
                            ListElement(titles[0]){

                            }
                            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                            ListElement(titles[1]){

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


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
            }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Tile(
    title: String,
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val NanumGothic = FontFamily(
        Font(R.font.nanumgothic_extrabold, FontWeight.ExtraBold),
        Font(R.font.nanumgothic_bold, FontWeight.Bold),
        Font(R.font.nanumgothic_regular, FontWeight.Normal)
    )
    Card(
        onClick = onClick,
        modifier = modifier
            .width(200.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
    ) {
        Box (
            modifier = Modifier
                .height(160.dp),
            contentAlignment = Alignment.BottomEnd
        ){
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 270f
                    )
                )

            )
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(7.dp),
                contentAlignment = Alignment.BottomEnd
            ){
                Text(title, style = TextStyle(color = Color.White, fontSize = 17.sp),
                    fontFamily = NanumGothic,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListElement(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    modifier = modifier
                        .padding(vertical = 10.dp, horizontal = 10.dp)
                )
            }
        }
    }
}

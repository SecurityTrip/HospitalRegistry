package com.example.hospitalregistry.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.hospitalregistry.MainActivity
import com.example.hospitalregistry.custom.AnalysisElement
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class AnalysisFragment : Fragment() {
    private var db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
                    ) {
                        BackButton(onBackPressed = {
                            // Создание Intent для перехода к другой активности
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.putExtra("source", "UserInfoActivity");
                            // Вызов активности по созданному Intent
                            startActivity(intent)
                        })

                    }
                    Spacer(modifier = Modifier)
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(15.dp))
                    ) {

                        var analysisList by remember { mutableStateOf<List<AnalysisElement>>(emptyList()) }

                        db.collection("analysis")
                            .whereEqualTo("id", FirebaseAuth.getInstance().currentUser?.uid)
                            .get()
                            .addOnSuccessListener { documents ->
                                val fetchedAnalysisList = mutableListOf<AnalysisElement>()
                                for (document in documents) {
                                    val tmp = (document["value"] as String?)?.let {
                                        (document["title"] as String?)?.let { it1 ->
                                            AnalysisElement(
                                                it1,
                                                it
                                            )
                                        }
                                    }
                                    if (tmp != null) {
                                        fetchedAnalysisList.add(tmp)
                                    }
                                }
                                analysisList = fetchedAnalysisList // Обновляем значение registrationsList
                            }
                            .addOnFailureListener { exception ->
                                // Handle error
                            }
                        
                        if (analysisList.isEmpty()) {
                            Text(text = "У Вас пока нет данных анализов"
                            , modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp))
                        } else {
                            AnalysisList(analysisList)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnalysisList(analysisList: List<AnalysisElement>) {
    Column {
        for (analysis in analysisList) {
            AnalysisListElement(analysis.getTitle(), analysis.getValue())
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@Composable
fun AnalysisListElement(
    title: String,
    value: String,

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Врач: $title, Дата: $value",
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 10.dp)
                )
            }
        }
    }
}
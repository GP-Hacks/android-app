package com.example.payments

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.payments.components.SearchTextField
import com.example.ui.theme.evolentaFamily

@Composable
fun PaymentsRoute() {

    var searchText by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val fullListItems = mapOf(
        "Детский сад" to Pair(R.drawable.detskiy_sad_icon, Color(0xFFB262C0)),
        "Транспортная карта" to Pair(R.drawable.transport_karta_icon, Color(0xFFE13437)),
        "Обркарта" to Pair(R.drawable.obrkarta_icon, Color(0xFF5780EA)),
        "ЖКХ" to Pair(R.drawable.zkh_icon, Color(0xFFFC9101)),
        "Газ по штрих-коду" to Pair(R.drawable.gaz_icon, Color(0xFF51AADB)),
        "Интернет" to Pair(R.drawable.internet_icon, Color(0xFF00B545)),
        "Телевидение" to Pair(R.drawable.tv_icon, Color(0xFF1D1D1D)),
        "Налоги, штрафы, госплатежи" to Pair(R.drawable.gos_icon, Color(0xFF265CE7)),
        "Штрафы ГИБДД" to Pair(R.drawable.gibdd_icon, Color(0xFF4A4A4A))
    )

    var listPayment by remember {
        mutableStateOf(fullListItems)
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(35.dp))
        SearchTextField(
            modifier = Modifier,
            text = searchText,
            onChangeMessage = {
                searchText = it
                listPayment = buildMap {
                    fullListItems.forEach { el ->
                        if (searchText.lowercase() in el.key.lowercase()) {
                            this[el.key] = el.value
                        }
                    }
                }
            },
            placeholder = "Поиск"
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Платежи", fontFamily = evolentaFamily, fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        listPayment.forEach {
            Button(
                onClick = {
                    Toast.makeText(context, "Раздел в разработке", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEDEDED).copy(0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start),
            ) {
                Icon(painter = painterResource(id = it.value.first), contentDescription = null, tint = it.value.second)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = it.key,
                    fontSize = 15.sp,
                    fontFamily = evolentaFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF040404)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}
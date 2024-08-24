package com.example.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.home.components.ImageBackground
import com.example.ui.theme.evolentaFamily
import com.example.ui.theme.mColors
import com.skydoves.cloudy.cloudy
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun HomeRoute(
    navigateToAuthScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        mColors.surface,
                        RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.uzori), contentDescription = null, modifier = Modifier
                        .fillMaxWidth())
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = mColors.surfaceContainer
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(text = "10% кешбэк за оплату ЖКХ", color = Color.Black, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, fontFamily = evolentaFamily)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Оплачивайте коммунальные \n" + "услуги Картой жителя РТ", color = Color.Black, fontSize = 12.sp, fontFamily = evolentaFamily)
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.icon1), contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Выпустите Карту жителя Татарстана, чтобы получить привелегиии", fontSize = 12.sp, color = Color(0xFF858585), fontWeight = FontWeight.Light, fontFamily = evolentaFamily)
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (!viewModel.checkAuth()) {
                    Button(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        onClick = { navigateToAuthScreen() },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = mColors.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Войти через АК Барс Банк", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp, fontFamily = evolentaFamily)
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Выпустить карту", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp, fontFamily = evolentaFamily)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
            ) {
                Icon(painter = painterResource(id = R.drawable.icon2), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Узнайте как защитить деньги", color = Color.Black, fontWeight = FontWeight.Normal, fontSize = 18.sp, fontFamily = evolentaFamily)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        mColors.surface,
                        RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.icon3), contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Другие карты", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.Black, fontFamily = evolentaFamily)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Контролируйте через приложение",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    color = Color(0xFF5E5E5E),
                    fontSize = 12.sp, fontFamily = evolentaFamily
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mColors.background,
                        contentColor = Color.Black
                    ),
                ) {
                    Text(text = "Обркарта", color = Color.Black, fontWeight = FontWeight.Normal, fontSize = 18.sp, fontFamily = evolentaFamily)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
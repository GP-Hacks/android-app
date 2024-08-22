package com.example.services

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.mColors

@Composable
fun ServicesRoute(
    navigateToReadersDiary: () -> Unit,
    navigateToVoting: () -> Unit,
    navigateToSchoolElectronDiary: () -> Unit,
    navigateToStocks: () -> Unit,
    navigateToPlaces: () -> Unit,
    navigateToPortalCare: () -> Unit,
    navigateToCharity: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.surface)
            .padding(16.dp)
    ) {
        Text(text = "Сервисы", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF51AADB)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(painter = painterResource(id = R.drawable.school_electron_diary_icon), contentDescription = null)
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Школьный электронный дневник", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .weight(1.5f),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFB262C0)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(painter = painterResource(id = R.drawable.charity_icon), contentDescription = null)
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Благотворительность", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text(text = "Вкладывайтесь в добро", fontSize = 10.sp, color = Color.White)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF373EA4)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(painter = painterResource(id = R.drawable.voting_icon), contentDescription = null)
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Голосовалка", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text(text = "Голосуйте за проекты", fontSize = 10.sp, color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .weight(1f)
                    .clickable {
                        navigateToStocks()
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE13437)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(painter = painterResource(id = R.drawable.stocks_red_icon), contentDescription = null)
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Акции", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text(text = "Скидки и кэшбэки", fontSize = 10.sp, color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF5780EA)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(painter = painterResource(id = R.drawable.poral_care_icon), contentDescription = null)
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Портал Забота", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text(text = "Социальные льготы и начисления", fontSize = 10.sp, color = Color.White)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .weight(1.5f)
                    .clickable {
                        navigateToPlaces()
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF00B545)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(painter = painterResource(id = R.drawable.places_icon), contentDescription = null)
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Татарстан. Места", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text(text = "Путешествуйте в родных краях", fontSize = 10.sp, color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFC9101)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(painter = painterResource(id = R.drawable.readers_diary_icon), contentDescription = null)
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Читательский билет", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text(text = "Сохраняйте в электронном виде", fontSize = 10.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
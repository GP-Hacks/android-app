package com.example.charity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.charity.R
import com.example.domain.model.CharityModel
import com.example.ui.theme.mColors

@Composable
fun FullInfoCharityDialog(
    charity: CharityModel,
    onDismissRequest: () -> Unit,
    onDonate: (Long) -> Unit
) {
    var currentScreen by remember { mutableIntStateOf(0) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = mColors.surface
            )
        ) {
            when (currentScreen) {
                0 -> InfoPage(charity = charity) {
                    currentScreen = 1
                }
                1 -> SelectSumForDonate(
                    onBack = {
                        currentScreen = 0
                    },
                    onDonate =  {
                        onDonate(it)
                    }
                )
            }
        }
    }

}

@Composable
fun InfoPage(
    charity: CharityModel,
    onNext: () -> Unit
) {
    var isLoading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // ДОБАВИТЬ ФОТКИ
//        val pagerState = rememberPagerState(0, 0f, pageCount = {
//            return@rememberPagerState 1
//        })
//        HorizontalPager(state = pagerState, modifier = Modifier.background(mColors.surface)) {
//            Box(
//                contentAlignment = Alignment.Center
//            ) {
//                AsyncImage(
//                    model = charity.,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(160.dp)
//                        .background(Color(0xFFE3E3E3), RoundedCornerShape(25.dp))
//                        .clip(RoundedCornerShape(25.dp)),
//                    onLoading = {
//                        isLoading = true
//                    },
//                    onSuccess = {
//                        isLoading = false
//                    },
//                    contentScale = ContentScale.Crop
//                )
//                if (isLoading) {
//                    CircularProgressIndicator(
//                        color = mColors.primary
//                    )
//                }
//            }
//        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = charity.name,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = charity.category,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .background(
                        Color(0xFF1E1E1E),
                        RoundedCornerShape(7.dp)
                    )
                    .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Уже собрано ${charity.current} р из ${charity.goal} р",
                fontSize = 11.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            LinearProgressIndicator(
                progress = {
                    charity.current.toFloat() / charity.goal.toFloat()
                },
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                trackColor = Color(0xFFE4E4E4),
                color = mColors.primary,
                strokeCap = StrokeCap.Round
            )

            if (charity.website != "") {
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Icon(painter = painterResource(id = R.drawable.site_icon), contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = charity.website, fontSize = 11.sp, color = mColors.primary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            if (charity.phone != "") {
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Icon(painter = painterResource(id = R.drawable.phone_icon), contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = charity.phone, fontSize = 11.sp, color = mColors.primary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = charity.description, fontSize = 11.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.width(32.dp))
                Button(
                    onClick = { onNext() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Пожертвовать", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(32.dp))
            }
        }
    }
}

@Composable
fun SelectSumForDonate(
    onBack: () -> Unit,
    onDonate: (Long) -> Unit
) {

}
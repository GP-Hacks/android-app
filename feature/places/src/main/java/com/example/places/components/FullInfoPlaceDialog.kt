package com.example.places.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.domain.model.PlaceModel
import com.example.places.R
import com.example.places.utils.convertMillisToDate
import com.example.ui.theme.mColors
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun FullInfoPlaceDialog(
    place: PlaceModel,
    onDismissRequest: () -> Unit,
    onBuyTicket: (Long, Long) -> Unit
) {
    var currentScreen by remember { mutableIntStateOf(0) }
    var isForward by remember { mutableStateOf(true) }
    var selectedDate by remember {
        mutableLongStateOf(0)
    }
    
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
                0 -> InfoPage(place = place) {
                    isForward = true
                    currentScreen = 1
                }
                1 -> DatePickerForBuyPage(
                    onBack = {
                        isForward = false
                        currentScreen = 0
                    },
                    onNext =  {
                        isForward = true
                        currentScreen = 2
                    },
                    onSelectDate = {
                        selectedDate = it
                    }
                )
                2 -> TimePickerForBuy(
                    times = listOf("00:10", "14:00", "15:00"),
                    onBack = {
                        isForward = false
                        currentScreen = 1
                    },
                    onBuy = {
                        onBuyTicket(selectedDate, it)
                        onDismissRequest()
                    },
                    currentDate = selectedDate
                )
            }
        }
    }
    
}

@Composable
fun InfoPage(
    place: PlaceModel,
    onNext: () -> Unit
) {
    var isLoading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val pagerState = rememberPagerState(0, 0f, pageCount = {
            return@rememberPagerState place.photos.size
        })
        HorizontalPager(state = pagerState, modifier = Modifier.background(mColors.surface)) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = place.photos[it],
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(Color(0xFFE3E3E3), RoundedCornerShape(25.dp))
                        .clip(RoundedCornerShape(25.dp)),
                    onLoading = {
                        isLoading = true
                    },
                    onSuccess = {
                        isLoading = false
                    },
                    contentScale = ContentScale.Crop
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        color = mColors.primary
                    )
                }
            }
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = place.name,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.location_icon), contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = place.location,
                    color = Color(0xFF616161),
                    fontSize = 10.sp,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .background(Color(0xFF00B545).copy(0.19f), RoundedCornerShape(7.dp))
                        .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp)
                ) {
                    Text(text = "1000 ₽", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00B545))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = place.category,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            if (place.website != "") {
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Icon(painter = painterResource(id = R.drawable.site_icon), contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = place.website, fontSize = 11.sp, color = Color.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            if (place.tel != "") {
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Icon(painter = painterResource(id = R.drawable.phone_icon), contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = place.tel, fontSize = 11.sp, color = Color.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            if (place.description != "") {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = place.description, fontSize = 11.sp, color = Color.Black)
            }

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
                    Text(text = "Купить билет", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(32.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerForBuyPage(
    onBack: () -> Unit,
    onNext: () -> Unit,
    onSelectDate: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onBack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Назад", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    onSelectDate(datePickerState.selectedDateMillis!!)
                    onNext()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = mColors.primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                enabled = (selectedDate != "") && ((datePickerState.selectedDateMillis ?: 0) >= System.currentTimeMillis())
            ) {
                Text(text = "Продолжить", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TimePickerForBuy(
    times: List<String>,
    onBack: () -> Unit,
    onBuy: (Long) -> Unit,
    currentDate: Long
) {
    var selectedTime by remember {
        mutableIntStateOf(0)
    }
    val currentTime: Long = SimpleDateFormat("HH:mm", Locale.getDefault()).parse(times[selectedTime])?.time ?: 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Выберите время", color = Color.Black, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        times.forEachIndexed { index, s ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedTime == index,
                    onClick = { selectedTime = index }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = s, color = Color.Black, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onBack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Назад", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    onBuy(timeStringToMillis(times[selectedTime]))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = mColors.primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                enabled = isTimeAfterCurrent(times[selectedTime], currentDate)
            ) {
                Text(text = "Купить", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

    }

}

fun timeStringToMillis(timeString: String): Long {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    val time = format.parse(timeString)
    return time?.time ?: 0L
}

fun isTimeAfterCurrent(timeString: String, dateMillis: Long): Boolean {
    val now = Calendar.getInstance()
    val currentTimeMillis = now.timeInMillis

    val date = Calendar.getInstance().apply {
        timeInMillis = dateMillis
    }

    date.set(Calendar.HOUR_OF_DAY, 0)
    date.set(Calendar.MINUTE, 0)
    date.set(Calendar.SECOND, 0)
    date.set(Calendar.MILLISECOND, 0)

    val timeMillis = timeStringToMillis(timeString)
    val targetTimeMillis = date.timeInMillis + timeMillis

    return targetTimeMillis > currentTimeMillis
}
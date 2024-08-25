package com.example.votes.components

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.common.model.ResultModel
import com.example.domain.model.FullInfoVoteModel
import com.example.ui.theme.evolentaFamily
import com.example.ui.theme.mColors
import com.example.votes.R
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.fullWidth
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoteFullInfoModalSheet(
    fullInfoVote: ResultModel<FullInfoVoteModel>,
    enabled: Boolean,
    onDismiss: () -> Unit,
    state: SheetState,
    onVote: (String) -> Unit
) {
    var currentPage by remember {
        mutableIntStateOf(0)
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = state
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            when (currentPage) {
                0 -> VoteFullInfoModalSheetInfo(
                    fullInfoVote = fullInfoVote,
                    onNext = {
                        when (fullInfoVote.data?.category) {
                            "rate" -> currentPage = 1
                            "choice" -> currentPage = 2
                            "petition" -> currentPage = 3
                        }
                    },
                    enabled = enabled,
                    onChart = {
                        currentPage = 4
                    }
                )
                1 -> VoteFullInfoModalSheetRate(
                    onVote = {
                        onVote(it)
                        onDismiss()
                    },
                    onBack = {
                        currentPage = 0
                    }
                )
                2 -> VoteFullInfoModalSheetChoice(
                    onVote = {
                        onVote(it)
                        onDismiss()
                    },
                    onBack = {
                        currentPage = 0
                    },
                    listAnswers = fullInfoVote.data!!.options ?: emptyList()
                )
                3 -> VoteFullInfoModalSheetPetition(
                    onVote = {
                        onVote(it)
                        onDismiss()
                    },
                    onBack = {
                        currentPage = 0
                    }
                )
                4 -> {
                    if (fullInfoVote.data!!.category == "rate") {
                        VoteFullInfoModalSheetMid(mid = fullInfoVote.data!!.mid!!) {
                            currentPage = 0
                        }
                    } else {
                        VoteFullInfoModalSheetChart(
                            if (fullInfoVote.data!!.category == "petition") {
                                buildMap {
                                    fullInfoVote.data!!.stats.forEach {
                                        this[if (it.key == "true") "Да" else "Нет"] = it.value
                                    }
                                }
                            } else {
                                fullInfoVote.data!!.stats
                            },
                            onBack = {
                                currentPage = 0
                            }
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun VoteFullInfoModalSheetInfo(
    fullInfoVote: ResultModel<FullInfoVoteModel>,
    onNext: () -> Unit,
    onChart: () -> Unit,
    enabled: Boolean
) {

    if (fullInfoVote.status == ResultModel.Status.SUCCESS) {
        var isLoading by remember {
            mutableStateOf(false)
        }
        var isError by remember {
            mutableStateOf(false)
        }

        Box(
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = fullInfoVote.data!!.photo,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(Color(0xFFEFF1F5), RoundedCornerShape(25.dp))
                    .clip(RoundedCornerShape(25.dp)),
                onSuccess = {
                    isLoading = false
                },
                onError ={
                    isError = true
                    isLoading = false
                },
                onLoading = {
                    isLoading = true
                },
                contentScale = ContentScale.Crop
            )
            if (isLoading) {
                CircularProgressIndicator(color = mColors.primary)
            }
            if (isError) {
                Icon(painter = painterResource(id = R.drawable.no_image_icon), contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = fullInfoVote.data!!.name,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp, fontFamily = evolentaFamily
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "До ${fullInfoVote.data!!.end}",
            color = Color.Black,
            fontSize = 10.sp, fontFamily = evolentaFamily
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = when (fullInfoVote.data!!.category) {
                "rate" -> "Рейтинг"
                "choice" -> "Опрос"
                "petition" -> "Петиция"
                else -> "null"
            },
            color = Color.White,
            modifier = Modifier
                .background(Color.Black, RoundedCornerShape(4.dp))
                .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
            fontFamily = evolentaFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = fullInfoVote.data!!.organization,
            color = Color.Black,
            fontSize = 10.sp, fontFamily = evolentaFamily
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = fullInfoVote.data!!.description, fontFamily = evolentaFamily, fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (fullInfoVote.data!!.rate != null) {
            Row {
                Text(
                    text = "Вы уже голосовали за: ",
                    color = Color.Black,
                    fontFamily = evolentaFamily,
                    fontSize = 12.sp
                )
                Text(
                    text = fullInfoVote.data!!.rate.toString(),
                    color = Color.Black,
                    fontFamily = evolentaFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else if (fullInfoVote.data!!.choice != null) {
            Row {
                Text(
                    text = "Вы уже голосовали за: ",
                    color = Color.Black,
                    fontFamily = evolentaFamily,
                    fontSize = 12.sp
                )
                Text(
                    text = fullInfoVote.data!!.choice.toString(),
                    color = Color.Black,
                    fontFamily = evolentaFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else if (fullInfoVote.data!!.support != null) {
            Row {
                Text(
                    text = "Вы уже голосовали за: ",
                    color = Color.Black,
                    fontFamily = evolentaFamily,
                    fontSize = 12.sp
                )
                Text(
                    text = when (fullInfoVote.data!!.support) {
                        "true" -> "Да"
                        "false" -> "Нет"
                        else -> "null"
                    },
                    color = Color.Black,
                    fontFamily = evolentaFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onChart() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Результаты", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = evolentaFamily)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    onNext()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = mColors.primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Проголосовать", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = evolentaFamily)
            }
        }
    } else if (fullInfoVote.status == ResultModel.Status.LOADING) {
        CircularProgressIndicator(color = mColors.primary)
    }

}

@Composable
fun VoteFullInfoModalSheetMid(
    mid: Float,
    onBack: () -> Unit
) {
    Text(
        text = "Результаты",
        fontFamily = evolentaFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
    Spacer(modifier = Modifier.height(8.dp))
    Row {
        Text(
            text = "Средняя оценка: ",
            color = Color.Black,
            fontFamily = evolentaFamily,
            fontSize = 12.sp
        )
        Text(
            text = mid.toString(),
            color = mColors.primary,
            fontFamily = evolentaFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {
            onBack()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "Назад", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = evolentaFamily)
    }
}

@Composable
fun VoteFullInfoModalSheetChart(
    data: Map<String, Int>,
    onBack: () -> Unit
) {
    val fullSum = data.values.sum()

    Text(
        text = "Результаты",
        fontFamily = evolentaFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
    Spacer(modifier = Modifier.height(8.dp))
    data.forEach {
        Row {
            Text(
                text = "${it.key}: ",
                color = Color.Black,
                fontFamily = evolentaFamily,
                fontSize = 12.sp
            )
            Text(
                text = "${it.value * 100 / fullSum}% (${it.value})",
                color = mColors.primary,
                fontFamily = evolentaFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
    Spacer(modifier = Modifier.height(12.dp))
    Button(
        onClick = {
            onBack()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "Назад", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = evolentaFamily)
    }
}

@Composable
fun VoteFullInfoModalSheetRate(
    onVote: (String) -> Unit,
    onBack: () -> Unit
) {
    var selectRate by remember {
        mutableIntStateOf(5)
    }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { selectRate = 1 },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (selectRate >= 1) Color.Yellow else Color.Gray
                )
            ) {
                Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = null)
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = { selectRate = 2 },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (selectRate >= 2) Color.Yellow else Color.Gray
                )
            ) {
                Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = null)
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = { selectRate = 3 },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (selectRate >= 3) Color.Yellow else Color.Gray
                )
            ) {
                Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = null)
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = { selectRate = 4 },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (selectRate >= 4) Color.Yellow else Color.Gray
                )
            ) {
                Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = null)
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = { selectRate = 5 },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (selectRate >= 5) Color.Yellow else Color.Gray
                )
            ) {
                Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
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
                Text(text = "Назад", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = evolentaFamily)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    onVote(selectRate.toString())
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = mColors.primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Проголосовать", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = evolentaFamily)
            }
        }
    }
}

@Composable
fun VoteFullInfoModalSheetPetition(
    onVote: (String) -> Unit,
    onBack: () -> Unit
) {
    var selectedVote by remember {
        mutableStateOf(true)
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedVote,
                onClick = {
                    selectedVote = true
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "За",
                color = Color.Black, fontFamily = evolentaFamily
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !selectedVote,
                onClick = {
                    selectedVote = false
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Против",
                color = Color.Black, fontFamily = evolentaFamily
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
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
                Text(text = "Назад", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = evolentaFamily)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    onVote(selectedVote.toString())
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = mColors.primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Проголосовать", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = evolentaFamily)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun VoteFullInfoModalSheetChoice(
    onVote: (String) -> Unit,
    onBack: () -> Unit,
    listAnswers: List<String>
) {
    if (listAnswers.isNotEmpty()) {
        var selectedVote by remember {
            mutableStateOf(0)
        }

        Column {
            listAnswers.forEachIndexed { index, s ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = index == selectedVote,
                        onClick = {
                            selectedVote = index
                        }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = s,
                        color = Color.Black, fontFamily = evolentaFamily
                    )
                }
                if (index != listAnswers.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
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
                    Text(text = "Назад", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = evolentaFamily)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        onVote(listAnswers[selectedVote])
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mColors.primary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Проголосовать", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = evolentaFamily)
                }
            }
        }
    }
}
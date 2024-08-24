package com.example.votes.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import com.example.ui.theme.mColors
import com.example.votes.R

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
                    enabled = enabled
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
            }
        }

    }
}

@Composable
fun VoteFullInfoModalSheetInfo(
    fullInfoVote: ResultModel<FullInfoVoteModel>,
    onNext: () -> Unit,
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
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "До ${fullInfoVote.data!!.end}",
            color = Color.Black,
            fontSize = 10.sp
        )
        Text(
            text = fullInfoVote.data!!.organization,
            color = Color.Black,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = fullInfoVote.data!!.description
        )
        Spacer(modifier = Modifier.height(8.dp))
        fullInfoVote.data!!.stats.forEach {
            Row {
                Text(
                    text = "${it.key}: ",
                    color = Color.Black,
                    fontSize = 10.sp
                )
                Text(
                    text = it.value.toString(),
                    color = mColors.primary,
                    fontSize = 10.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(32.dp))
            Button(
                onClick = { onNext() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                enabled = enabled,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Проголосовать", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(32.dp))
        }
    } else if (fullInfoVote.status == ResultModel.Status.LOADING) {
        CircularProgressIndicator(color = mColors.primary)
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
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = { selectRate = 2 },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (selectRate >= 2) Color.Yellow else Color.Gray
                )
            ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = { selectRate = 3 },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (selectRate >= 3) Color.Yellow else Color.Gray
                )
            ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = { selectRate = 4 },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (selectRate >= 4) Color.Yellow else Color.Gray
                )
            ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = { selectRate = 5 },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (selectRate >= 5) Color.Yellow else Color.Gray
                )
            ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
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
                Text(text = "Назад", fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
                Text(text = "Проголосовать", fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
                color = Color.Black
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
                color = Color.Black
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
                Text(text = "Назад", fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
                Text(text = "Проголосовать", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
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
                        color = Color.Black
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
                    Text(text = "Назад", fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
                    Text(text = "Проголосовать", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
package com.example.votes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.model.ResultModel
import com.example.ui.theme.mColors
import com.example.votes.components.VotesCard

@Composable
fun VotesRoute(
    viewModel: VotesViewModel = hiltViewModel()
) {
    val listVotes by viewModel.listVotes.collectAsState()

    val listState = rememberLazyListState()
    var paddingState by remember { mutableStateOf(80.dp) }
    val firstVisibleItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }

    LaunchedEffect(firstVisibleItemIndex.value) {
        val maxPadding = 80.dp
        val minPadding = 16.dp

        paddingState = if (firstVisibleItemIndex.value > 0) {
            minPadding
        } else {
            maxPadding
        }
    }
    val paddingAnimation by animateDpAsState(targetValue = paddingState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF373EA4))
    ) {
        Spacer(modifier = Modifier.height(paddingAnimation))
        Text(
            text = "Голос",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Голосуй в опросе и увидишь изменения во всей республике",
            color = Color(0xFFB7CBFF),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.surface, RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (listVotes.status == ResultModel.Status.LOADING) {
                item {
                    CircularProgressIndicator(color = mColors.primary)
                }
            } else if (listVotes.status == ResultModel.Status.SUCCESS && listVotes.data is List) {
                listVotes.data!!.forEachIndexed { index, vote ->
                    item {
                        VotesCard(
                            name = vote.name,
                            image = vote.photo,
                            onClick = {

                            }
                        )
                        if (index != listVotes.data!!.size - 1) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

        }
    }

}
package com.example.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.components.NewsCard
import com.example.ui.theme.mColors

@Composable
fun NewsRoute(
    viewModel: NewsViewModel = hiltViewModel()
) {
    val listNews = viewModel.listNews.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.loadNews()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.surface)
    ) {
        item {
            Image(painter = painterResource(id = R.drawable.group_23), contentDescription = null, modifier = Modifier.fillMaxWidth())
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Новости", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        }
        items(listNews.itemCount) {
            Spacer(modifier = Modifier.height(8.dp))
            NewsCard(title = listNews[it]?.title ?: "Название", image = listNews[it]?.pictureUrl, text = listNews[it]?.content ?: "Описание")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
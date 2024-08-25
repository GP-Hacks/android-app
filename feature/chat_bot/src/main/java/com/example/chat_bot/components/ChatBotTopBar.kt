package com.example.chat_bot.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_bot.R
import com.example.ui.theme.evolentaFamily
import com.example.ui.theme.mColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotTopBar(
    modifier: Modifier = Modifier,
    title: String,
    image: String,
    onBackPressed: () -> Unit,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
) {

    TopAppBar(
        title = {
            Text(text = title, color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 20.sp, fontFamily = evolentaFamily)
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chat_bot_avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black)
            }
        },
        colors = colors
    )

}
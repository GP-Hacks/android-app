package com.example.chat_bot.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.mColors

@Composable
fun Message(
    modifier: Modifier = Modifier,
    text: String,
    isUserMessage: Boolean
) {

    Row(
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        if (isUserMessage) {
            Spacer(modifier = Modifier.weight(1f))
            UserMessage(text = text)
        } else {
            BotMessage(text = text)
            Spacer(modifier = Modifier.weight(1f))
        }
    }

}

@Composable
fun UserMessage(
    text: String
) {
    Box(
        modifier = Modifier
            .background(Color.Black, RoundedCornerShape(20.dp))
            .padding(12.dp)
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.Normal, fontSize = 12.sp)
    }
}

@Composable
fun BotMessage(
    text: String
) {
    Box(
        modifier = Modifier
            .background(mColors.surfaceContainer, RoundedCornerShape(20.dp))
            .padding(12.dp)
    ) {
        Text(text = text, color = Color.Black, fontWeight = FontWeight.Normal, fontSize = 12.sp)
    }
}
package com.example.chat_bot.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_bot.R
import com.example.ui.theme.evolentaFamily
import com.example.ui.theme.mColors

@Composable
fun MessageTextField(
    modifier: Modifier,
    text: String,
    onSendButtonClick: () -> Unit,
    onChangeMessage: (String) -> Unit,
    placeholder: String,
    enabled: Boolean
) {

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(30.dp)
    ) {
        TextField(
            maxLines = 6,
            modifier = Modifier
                .fillMaxWidth()
                .height(47.dp),
            colors = TextFieldDefaults
                .colors(
                    focusedContainerColor = mColors.surface,
                    unfocusedContainerColor = mColors.surface,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledContainerColor = mColors.background,
                    disabledTrailingIconColor = Color.Gray
                ),
            shape = RoundedCornerShape(30.dp),
            value = text,
            onValueChange = { onChangeMessage(it) },
            placeholder = {
                Text(text = placeholder, color = Color(0xFFA1A1A1), fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = evolentaFamily)
            },
            trailingIcon = {
                IconButton(
                    onClick = { onSendButtonClick() },
                    enabled = enabled
                ) {
                    Icon(painter = painterResource(id = R.drawable.send_icon), contentDescription = null)
                }
            },
            enabled = enabled
        )
    }

}

@Composable
fun BottomShadow(alpha: Float = 0.1f, height: Dp = 8.dp) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(height)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Black.copy(alpha = alpha),
                    Color.Transparent,
                )
            )
        )
    )
}
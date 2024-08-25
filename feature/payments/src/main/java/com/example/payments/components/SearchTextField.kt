package com.example.payments.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.evolentaFamily
import com.example.ui.theme.mColors

@Composable
fun SearchTextField(
    modifier: Modifier,
    text: String,
    onChangeMessage: (String) -> Unit,
    placeholder: String
) {

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(30.dp)
    ) {
        TextField(
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
//                .height(50.dp),
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
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color(0xFFC5C5C5))
            }
        )
    }

}
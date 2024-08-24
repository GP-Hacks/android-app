package com.example.stocks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.stocks.R
import com.example.ui.theme.evolentaFamily
import com.example.ui.theme.mColors

@Composable
fun PartnerCard(
    image: String?,
    title: String,
    category: String,
    categoryColor: String,
    date: String?,
    value: String?,
    openDialog: () -> Unit
) {
    var isLoading by remember {
        mutableStateOf(false)
    }
    var isError by remember {
        mutableStateOf(false)
    }

    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.clickable {
            openDialog()
        }
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color(0xFFE3E3E3), RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp)),
                    onLoading = {
                        isLoading = true
                    },
                    onSuccess = {
                        isLoading = false
                    },
                    onError = {
                        isLoading = false
                        isError = true
                    },
                    contentScale = ContentScale.Crop
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        color = mColors.primary
                    )
                }
                if (isError) {
                    Icon(painter = painterResource(id = R.drawable.no_image_icon), contentDescription = null)
                }
            }
            if (value != null) {
                Text(
                    text = value,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 16.dp, end = 16.dp)
                        .background(Color(0xFF00B545), RoundedCornerShape(5.dp))
                        .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold, fontFamily = evolentaFamily
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black, fontFamily = evolentaFamily
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .background(
                    Color(0xFF1E1E1E),
                    RoundedCornerShape(7.dp)
                )
                .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp), fontFamily = evolentaFamily
        )

        if (date != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "до $date",
                color = Color(0xFF656565),
                fontSize = 12.sp, fontFamily = evolentaFamily
            )
        }
    }

}
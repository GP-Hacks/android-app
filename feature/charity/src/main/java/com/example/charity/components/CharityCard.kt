package com.example.charity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.charity.R
import com.example.ui.theme.evolentaFamily
import com.example.ui.theme.mColors

@Composable
fun CharityCard(
    image: String?,
    title: String,
    category: String,
    openDialog: () -> Unit
) {
    var isLoading by remember {
        mutableStateOf(false)
    }
    var isError by remember {
        mutableStateOf(false)
    }

    Card(
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = mColors.surface
        ),
        modifier = Modifier.clickable {
            openDialog()
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold, fontFamily = evolentaFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold, fontFamily = evolentaFamily,
                color = Color.White,
                modifier = Modifier
                    .background(
                        Color(0xFF1E1E1E),
                        RoundedCornerShape(7.dp)
                    )
                    .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp)
            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Подробнее",
//                fontSize = 10.sp,
//                fontWeight = FontWeight.Bold, fontFamily = evolentaFamily,
//                color = mColors.primary,
//                modifier = Modifier
//                    .clickable {
//                        openDialog()
//                    }
//            )

        }
        Box(
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Color(0xFFE3E3E3))
                    .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)),
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
    }

}
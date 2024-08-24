package com.example.news.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.news.R
import com.example.ui.theme.evolentaFamily
import com.example.ui.theme.mColors

@Composable
fun NewsCard(
    title: String,
    image: String?,
    text: String
) {
    var isLoading by remember {
        mutableStateOf(false)
    }
    var isError by remember {
        mutableStateOf(false)
    }
    var isOpen by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(350.dp)
                .height(170.dp)
                .background(Color(0xFFEFF1F5), RoundedCornerShape(25.dp))
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(25.dp)),
                model = image,
                contentDescription = null,
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, color = Color.Black, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, fontFamily = evolentaFamily)
        Spacer(modifier = Modifier.height(4.dp))
        if (!isOpen) {
            Text(text = text, maxLines = 2, overflow = TextOverflow.Ellipsis, color = Color.Black, fontSize = 14.sp, fontFamily = evolentaFamily)
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Показать ещё",
                    modifier = Modifier
                        .clickable {
                            isOpen = true
                        },
                    color = mColors.primary,
                    fontSize = 10.sp, fontFamily = evolentaFamily
                )
            }
        } else {
            Text(text = text, color = Color.Black, fontSize = 14.sp, fontFamily = evolentaFamily)
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Скрыть",
                    modifier = Modifier
                        .clickable {
                            isOpen = false
                        },
                    color = mColors.primary,
                    fontSize = 10.sp, fontFamily = evolentaFamily
                )
            }
        }
    }

}
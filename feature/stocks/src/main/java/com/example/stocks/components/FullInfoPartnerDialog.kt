package com.example.stocks.components

import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.domain.model.PartnersModel
import com.example.stocks.R
import com.example.ui.theme.mColors

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FullInfoPartnerDialog(
    partner: PartnersModel,
    onDismissRequest: () -> Unit
) {

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = mColors.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.partner_icon), contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (partner.cashBack != null) "Кэшбэк ${partner.cashBack}%" else if (partner.percentSale != null) "Скидка ${partner.percentSale}%" else "null",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(
                    containerColor = mColors.surface
                )
            ) {
                var isLoading by remember {
                    mutableStateOf(false)
                }
                var isError by remember {
                    mutableStateOf(false)
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = partner.backgroundImage,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(Color(0xFFE3E3E3), RoundedCornerShape(25.dp))
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = partner.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (partner.dateEnd != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color(0xFF00B545), RoundedCornerShape(8.dp))
                                .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp)
                        ) {
                            Text(
                                text = "Акция действует до ",
                                color = Color.White,
                                fontSize = 11.sp
                            )
                            Text(
                                text = partner.dateEnd!!,
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = partner.description,
                        fontSize = 13.sp,
                        color = Color.Black
                    )

                    if (partner.site != "" && partner.phone != "") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Подробнее по ссылке", fontSize = 13.sp, color = Color.Black)
                        Text(text = partner.site, color = mColors.primary, fontSize = 13.sp)
                        Text(text = "или по телефону", fontSize = 13.sp, color = Color.Black)
                        Text(text = partner.phone, color = mColors.primary, fontSize = 13.sp)
                    } else if (partner.site != "") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Подробнее по ссылке", fontSize = 13.sp, color = Color.Black)
                        Text(text = partner.site, color = mColors.primary, fontSize = 13.sp)
                    } else if (partner.phone != "") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Подробнее по телефону", fontSize = 13.sp, color = Color.Black)
                        Text(text = partner.phone, color = mColors.primary, fontSize = 13.sp)
                    }
                }

            }
        }
    }

}

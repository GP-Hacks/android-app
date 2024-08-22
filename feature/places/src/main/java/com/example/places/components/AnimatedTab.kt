package com.example.places.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedTab(
    items: List<String>,
    modifier: Modifier,
    indicatorPadding: Dp = 4.dp,
    selectedItemIndex: Int = 0,
    onSelectedTab: (index: Int) -> Unit
) {

    var tabWidth by remember { mutableStateOf(0.dp) }

    val indicatorOffset: Dp by animateDpAsState(
        if (selectedItemIndex == 0) {
            tabWidth * (selectedItemIndex / items.size.toFloat())
        } else {
            tabWidth * (selectedItemIndex / items.size.toFloat()) - indicatorPadding
        }
    )

    val density = LocalDensity.current
    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                tabWidth = with(density) {
                    coordinates.size.width.toDp()
                }
            }
            .background(color = Color(0xFF008935), shape = RoundedCornerShape(10.dp))
    ) {

        MyTabIndicator(
            modifier = Modifier
                .padding(indicatorPadding)
                .fillMaxHeight()
                .width(tabWidth / items.size - indicatorPadding),
            indicatorOffset = indicatorOffset
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, title ->
                MyTabItem(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(tabWidth / items.size),
                    onClick = {
                        onSelectedTab(index)
                    },
                    title = title,
                    isSelected = index == selectedItemIndex
                )
            }
        }

    }
}

@Composable
private fun MyTabIndicator(
    modifier: Modifier,
    indicatorOffset: Dp,
) {
    Box(
        modifier = modifier
            .offset(x = indicatorOffset)
            .clip(RoundedCornerShape(7.dp))
            .background(Color.White)
    )
}


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun MyTabItem(
    modifier: Modifier,
    onClick: () -> Unit,
    title: String,
    isSelected: Boolean
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, color = if (isSelected) Color.Black else Color.White, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
    }
}
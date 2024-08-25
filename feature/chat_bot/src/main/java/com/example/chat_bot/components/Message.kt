package com.example.chat_bot.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ui.theme.evolentaFamily
import com.example.ui.theme.mColors

@Composable
fun Message(
    modifier: Modifier = Modifier,
    text: String,
    isUserMessage: Boolean,
    isError: Boolean,
    navigateToCharity: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToNews: () -> Unit,
    navigateToPayments: () -> Unit,
    navigateToPlaces: () -> Unit,
    navigateToPortalCare: () -> Unit,
    navigateToServices: () -> Unit,
    navigateToStocks: () -> Unit,
    navigateToVotes: () -> Unit,
) {

    Row(
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp, start = if (isUserMessage) 72.dp else 0.dp, end = if (isUserMessage) 0.dp else 72.dp)
    ) {
        if (isUserMessage) {
            Spacer(modifier = Modifier.weight(1f))
            UserMessage(text = text, isError = isError)
        } else {
            BotMessage(text = text, isError = isError, navigateToCharity, navigateToHome, navigateToNews, navigateToPayments, navigateToPlaces, navigateToPortalCare, navigateToServices, navigateToStocks, navigateToVotes)
            Spacer(modifier = Modifier.weight(1f))
        }
    }

}

@Composable
fun UserMessage(
    text: String,
    isError: Boolean
) {
    Box(
        modifier = Modifier
            .background(
                Color.Black,
                RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
            )
            .padding(12.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 12.sp, fontFamily = evolentaFamily)
    }
}

@Composable
fun BotMessage(
    text: String,
    isError: Boolean,
    navigateToCharity: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToNews: () -> Unit,
    navigateToPayments: () -> Unit,
    navigateToPlaces: () -> Unit,
    navigateToPortalCare: () -> Unit,
    navigateToServices: () -> Unit,
    navigateToStocks: () -> Unit,
    navigateToVotes: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                mColors.surfaceContainer,
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 20.dp)
            )
            .padding(12.dp)
    ) {
        val regex = Regex("""\[|\]|\|""")
        val annotatedString = buildAnnotatedString {
            val l: List<String> = regex.split(text).filter { it.isNotEmpty() }
            for (i in 1..l.size) {
                if (i % 2 != 0) {
                    append(l[i - 1])
                } else {
                    pushStringAnnotation(tag = l[i - 1], annotation = "nav")
                    withStyle(style = SpanStyle(color = mColors.primary, fontWeight = FontWeight.Bold)) {
                        append(
                            when (l[i - 1]) {
                                "charity" -> "Благотворительность"
                                "home" -> "Главная"
                                "news" -> "Новости"
                                "payments" -> "Платежи"
                                "places" -> "Татарстан. Места"
                                "portal_care" -> "Портал Забота"
                                "services" -> "Сервисы"
                                "stocks" -> "Акции"
                                "votes" -> "Голос"
                                else -> ""
                            }
                        )
                    }
                    pop()
                }
            }
        }
        Log.i("MESSAGE BOT", annotatedString.text)
        Log.i("MESSAGE BOT", text)
        Log.i("MESSAGE BOT", text.split(regex).filter { it.isBlank() }.toString())
        Log.i("MESSAGE BOT", text.split(regex).toString())

        ClickableText(
            text = annotatedString,
            style = TextStyle(
                color = Color.Black,
                fontFamily = evolentaFamily,
                fontSize = 12.sp
            ),
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "charity", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navigateToCharity()
                    }
                annotatedString.getStringAnnotations(tag = "home", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navigateToHome()
                    }
                annotatedString.getStringAnnotations(tag = "news", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navigateToNews()
                    }
                annotatedString.getStringAnnotations(tag = "payments", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navigateToPayments()
                    }
                annotatedString.getStringAnnotations(tag = "places", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navigateToPlaces()
                    }
                annotatedString.getStringAnnotations(tag = "portal_care", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navigateToPortalCare()
                    }
                annotatedString.getStringAnnotations(tag = "services", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navigateToServices()
                    }
                annotatedString.getStringAnnotations(tag = "stocks", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navigateToStocks()
                    }
                annotatedString.getStringAnnotations(tag = "votes", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navigateToVotes()
                    }
            }
        )
    }
}

@Composable
fun BotMessageLoading() {
    Box(
        modifier = Modifier
            .background(
                mColors.surfaceContainer,
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 20.dp)
            )
            .padding(12.dp)
    ) {
        CircularProgressIndicator(
            color = mColors.primary
        )
    }
}
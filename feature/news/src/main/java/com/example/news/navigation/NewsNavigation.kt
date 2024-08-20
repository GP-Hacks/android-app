package com.example.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.news.NewsRoute
import kotlinx.serialization.Serializable

@Serializable
object News

fun NavController.navigateToNews(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(News, navOptions)

fun NavGraphBuilder.newsScreen() = composable<News> {
    NewsRoute()
}
package com.example.tatarstanresidentcard.navigation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.auth.navigation.Auth
import com.example.auth.navigation.authScreen
import com.example.auth.navigation.navigateToAuth
import com.example.charity.navigation.charityScreen
import com.example.charity.navigation.navigateToCharity
import com.example.chat_bot.navigation.chatBotScreen
import com.example.chat_bot.navigation.navigateToChatBot
import com.example.home.navigation.Home
import com.example.home.navigation.homeScreen
import com.example.home.navigation.navigateToHome
import com.example.news.navigation.News
import com.example.news.navigation.navigateToNews
import com.example.news.navigation.newsScreen
import com.example.places.navigation.navigateToPlaces
import com.example.places.navigation.placesScreen
import com.example.portal_care.navigation.navigateToPortalCare
import com.example.portal_care.navigation.portalCareScreen
import com.example.services.navigation.Services
import com.example.services.navigation.navigateToServices
import com.example.services.navigation.servicesScreen
import com.example.stocks.navigation.navigateToStocks
import com.example.stocks.navigation.stocksScreen
import com.example.tatarstanresidentcard.R
import com.example.ui.theme.mColors

@Composable
fun TatarstanResidentCardNavHost(
    modifier: Modifier = Modifier,
    startDestination: Any = Home,
    navController: NavHostController = rememberNavController()
) {
    var currentRoute by remember {
        mutableStateOf(startDestination)
    }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRouteReal = currentBackStackEntry?.destination?.route
    val bottomBarVisible = when (currentRouteReal) {
        "com.example.home.navigation.Home" -> true
        "com.example.services.navigation.Services" -> true
        "com.example.stocks.navigation.Stocks" -> true
        "com.example.news.navigation.News" -> true
        "com.example.places.navigation.Places" -> true
        "com.example.places.navigation.Charity" -> true
        else -> false
    }
    Log.i("ROUTE CURR", currentRouteReal.toString())

    Scaffold(
        bottomBar = { if (bottomBarVisible) BottomNavigationBar(navController = navController, currentRoute = currentRoute, changeRoute = { currentRoute = it }) }
    ) {
        NavHost(
            modifier = modifier.padding(bottom = it.calculateBottomPadding()),
            navController = navController,
            startDestination = startDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popExitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None}
        ) {
            chatBotScreen {
                navController.popBackStack()
            }
            homeScreen {
                navController.navigateToAuth()
            }
            newsScreen()
            servicesScreen(
                navigateToPlaces = {
                    navController.navigateToPlaces()
                },
                navigateToReadersDiary = {},
                navigateToVoting = {},
                navigateToSchoolElectronDiary = {},
                navigateToStocks = {
                    navController.navigateToStocks()
                },
                navigateToPortalCare = {
                    navController.navigateToPortalCare()
                },
                navigateToCharity = {
                    navController.navigateToCharity()
                }
            )
            stocksScreen()
            placesScreen()
            charityScreen()
            portalCareScreen {
                navController.popBackStack()
            }
            authScreen {
                navController.navigateToHome {
                    popUpTo(0)
                }
            }
        }
    }

}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentRoute: Any,
    changeRoute: (Any) -> Unit
) {
    NavigationBar(
        containerColor = Color.Transparent
    ) {
        NavigationBarItem(
            selected = currentRoute is Home,
            onClick = {
                changeRoute(Home)
                navController.navigateToHome()
            },
            label = {
                Text(text = "Главная")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = mColors.primary,
                selectedTextColor = mColors.primary,
                indicatorColor = Color.Transparent,
                unselectedIconColor = mColors.onTertiary,
                unselectedTextColor = mColors.onTertiary
            ),
            icon = { Icon(painter = painterResource(id = R.drawable.house_icon), contentDescription = null) }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
//                changeRoute(Stocks)
//                navController.navigateToStocks()
            },
            label = {
                Text(text = "Платежи")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = mColors.primary,
                selectedTextColor = mColors.primary,
                indicatorColor = Color.Transparent,
                unselectedIconColor = mColors.onTertiary,
                unselectedTextColor = mColors.onTertiary
            ),
            icon = { Icon(painter = painterResource(id = R.drawable.payments_icon), contentDescription = null) }
        )
        ElevatedButton(
            modifier = Modifier.size(50.dp),
            onClick = { navController.navigateToChatBot() },
            shape = RoundedCornerShape(15.dp),
            elevation = ButtonDefaults.buttonElevation(4.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = mColors.onSecondary,
                contentColor = Color.Transparent
            )
        ) {
            Icon(painter = painterResource(id = R.drawable.stocks_icon), contentDescription = null)
        }
        NavigationBarItem(
            selected = currentRoute is Services,
            onClick = {
                changeRoute(Services)
                navController.navigateToServices()
            },
            label = {
                Text(text = "Сервисы")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = mColors.primary,
                selectedTextColor = mColors.primary,
                indicatorColor = Color.Transparent,
                unselectedIconColor = mColors.onTertiary,
                unselectedTextColor = mColors.onTertiary
            ),
            icon = { Icon(painter = painterResource(id = R.drawable.services_icon), contentDescription = null) }
        )
        NavigationBarItem(
            selected = currentRoute is News,
            onClick = {
                changeRoute(News)
                navController.navigateToNews()
            },
            label = {
                Text(text = "Новости")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = mColors.primary,
                selectedTextColor = mColors.primary,
                indicatorColor = Color.Transparent,
                unselectedIconColor = mColors.onTertiary,
                unselectedTextColor = mColors.onTertiary
            ),
            icon = { Icon(painter = painterResource(id = R.drawable.news_icon), contentDescription = null) }
        )
    }
}
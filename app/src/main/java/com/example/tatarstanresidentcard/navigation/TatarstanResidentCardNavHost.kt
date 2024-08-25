package com.example.tatarstanresidentcard.navigation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import com.example.payments.navigation.Payments
import com.example.payments.navigation.navigateToPayments
import com.example.payments.navigation.paymentsScreen
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
import com.example.ui.theme.evolentaFamily
import com.example.ui.theme.mColors
import com.example.votes.navigation.navigateToVotes
import com.example.votes.navigation.votesScreen

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
        "com.example.charity.navigation.Charity" -> true
        "com.example.votes.navigation.Votes" -> true
        "com.example.portal_care.navigation.PortalCare" -> true
        "com.example.payments.navigation.Payments" -> true
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
            chatBotScreen(
                navigateToVotes = {
                    navController.navigateToVotes()
                },
                navigateToServices = {
                    navController.navigateToServices()
                },
                navigateToPayments = {
                    navController.navigateToPayments()
                },
                navigateToNews = {
                    navController.navigateToNews()
                },
                navigateToHome = {
                    navController.navigateToHome()
                },
                navigateToPlaces = {
                    navController.navigateToPlaces()
                },
                navigateToStocks = {
                    navController.navigateToStocks()
                },
                navigateToPortalCare = {
                    navController.navigateToPortalCare()
                },
                navigateToCharity = {
                    navController.navigateToCharity()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
            homeScreen {
                navController.navigateToAuth()
            }
            newsScreen()
            servicesScreen(
                navigateToPlaces = {
                    navController.navigateToPlaces()
                },
                navigateToReadersDiary = {},
                navigateToVotes = {
                    navController.navigateToVotes()
                },
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
            votesScreen()
            paymentsScreen()
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
                navController.navigateToHome {
                    popUpTo(0)
                }
            },
            label = {
                Text(text = "Главная", fontFamily = evolentaFamily)
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
            selected = currentRoute is Payments,
            onClick = {
                changeRoute(Payments)
                navController.navigateToPayments {
                    popUpTo(0)
                }
            },
            label = {
                Text(text = "Платежи", fontFamily = evolentaFamily)
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
//        ElevatedButton(
//            modifier = Modifier.size(50.dp),
//            onClick = { navController.navigateToChatBot() },
//            shape = RoundedCornerShape(15.dp),
//            elevation = ButtonDefaults.buttonElevation(4.dp),
//            colors = ButtonDefaults.elevatedButtonColors(
//                containerColor = mColors.primary
//            )
//        ) {
//            Image(painter = painterResource(id = R.drawable.chat_bot_icon), contentDescription = null, modifier = Modifier.fillMaxSize())
//        }
        Card(
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    navController.navigateToChatBot()
                },
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.chat_bot_icon), contentDescription = null, modifier = Modifier.fillMaxSize())
        }
        NavigationBarItem(
            selected = currentRoute is Services,
            onClick = {
                changeRoute(Services)
                navController.navigateToServices {
                    popUpTo(0)
                }
            },
            label = {
                Text(text = "Сервисы", fontFamily = evolentaFamily)
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
                navController.navigateToNews {
                    popUpTo(0)
                }
            },
            label = {
                Text(text = "Новости", fontFamily = evolentaFamily)
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

/*
Ты будешь подсказывать пользователю название код если ему понадабится информация, которая в нем находится. Ты обязан выдавать ответ с кодом раздела если требующаяся информация пользователю там находится. Вот список разделов, до тире идет тег, а после тире описание:
1) код - [|charity|], название - Благотворительность, слоган - вкладывайтесь в добро, описание - это один из сервисов, в нем люди могут пожертвовать свои деньги на помощь людям, животным или организациям. В нем есть поиск, фильтр по категориям и список того, куда можно пожертвовать. Пожертвовать можно только если пользователь авторизован
2) код - [|home|], название - Главная, описание - главная страница, на ней особо ничего нет, только кнопки для навигации в другие разделы
3) код - [|news|], название - Новости, описание - страница со списком новостей
4) код - [|payments|], название - Платежи, описание - страница где собраны различные категории платежей: Детский сад, Транспортная карта, Обркарта, ЖКХ, Газ по штрих-коду, Интернет, Телевидение, Налоги, Штрафы, Госплатежи, Штрафы гибдд
5) код - [|places|], название - Татарстан. Места, описание - сервис, который предоставляет список мест для посещения в татарстане, в нем есть поиск, фильтры по категориям, карта мест, список купленных билетов. В нем можно покупать билеты на места посещения. За 15 минут до мероприятия на которое куплен билет приходит пуш уведомление с напоминанием
6) код - [|portal_care|], название - Портал забота.
7) код - [|services|], название - Сервисы, это экран на котором собраны все сервисы: Благотворительность, Портал забота, Татарстан Места, Читательский билет, Голос, Акции, Секции
8) код - [|stocks|], название - Акции, это сервис, который показывает пользователю акции от партнеров, в нем есть фильтры по категориям и поиск
9) код - [|votes|], название - Голос, это сервис, который позволяет пользователю голосовать за проекты, оценивать их и голосовать за варинты развития
 */
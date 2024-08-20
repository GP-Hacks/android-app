package com.example.services.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.services.ServicesRoute
import kotlinx.serialization.Serializable

@Serializable
object Services

fun NavController.navigateToServices(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(Services, navOptions)

fun NavGraphBuilder.servicesScreen(
    navigateToReadersDiary: () -> Unit,
    navigateToVoting: () -> Unit,
    navigateToSchoolElectronDiary: () -> Unit,
    navigateToStocks: () -> Unit,
    navigateToPlaces: () -> Unit,
    navigateToPortalCare: () -> Unit
) = composable<Services> {
//    changeVisibleNavBar()
    ServicesRoute(navigateToReadersDiary, navigateToVoting, navigateToSchoolElectronDiary, navigateToStocks, navigateToPlaces, navigateToPortalCare)
}
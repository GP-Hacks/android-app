package com.example.services.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
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
    navigateToVotes: () -> Unit,
    navigateToSchoolElectronDiary: () -> Unit,
    navigateToStocks: () -> Unit,
    navigateToPlaces: () -> Unit,
    navigateToPortalCare: () -> Unit,
    navigateToCharity: () -> Unit
) = composable<Services> {
    ServicesRoute(navigateToReadersDiary, navigateToVotes, navigateToSchoolElectronDiary, navigateToStocks, navigateToPlaces, navigateToPortalCare, navigateToCharity)
}
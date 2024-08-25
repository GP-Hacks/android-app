package com.example.payments.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.payments.PaymentsRoute
import kotlinx.serialization.Serializable

@Serializable
object Payments

fun NavController.navigateToPayments(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(Payments, navOptions)

fun NavGraphBuilder.paymentsScreen() = composable<Payments> {
    PaymentsRoute()
}
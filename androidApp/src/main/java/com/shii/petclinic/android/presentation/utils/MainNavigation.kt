package com.shii.petclinic.android.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shii.petclinic.android.presentation.tabs.booking.BookingDetailsScreen
import com.shii.petclinic.android.presentation.tabs.booking.BookingScreen
import com.shii.petclinic.android.presentation.tabs.home.HomeDetailsScreen
import com.shii.petclinic.android.presentation.tabs.home.HomeScreen
import com.shii.petclinic.android.presentation.tabs.profile.ProfileDetailsScreen
import com.shii.petclinic.android.presentation.tabs.profile.ProfileScreen
import com.shii.petclinic.android.presentation.utils.NavigationRouteConstant.BOOKING_DETAILS_NAV_ROUTE
import com.shii.petclinic.android.presentation.utils.NavigationRouteConstant.HOME_DETAILS_NAV_ROUTE
import com.shii.petclinic.android.presentation.utils.NavigationRouteConstant.PROFILE_DETAILS_NAV_ROUTE

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    composable(BottomNavItem.Home.route) {
        HomeScreen(onDetailsClick = {
            navController.navigate(HOME_DETAILS_NAV_ROUTE)
        })
    }
    composable(HOME_DETAILS_NAV_ROUTE) {
        HomeDetailsScreen()
    }
}

fun NavGraphBuilder.bookingNavGraph(navController: NavController) {
    composable(BottomNavItem.Booking.route) {
        BookingScreen(onDetailsClick = {
            navController.navigate(BOOKING_DETAILS_NAV_ROUTE)
        })
    }
    composable(BOOKING_DETAILS_NAV_ROUTE) {
        BookingDetailsScreen()
    }
}

fun NavGraphBuilder.profileNavGraph(navController: NavController) {
    composable(BottomNavItem.Profile.route) {
        ProfileScreen(onDetailsClick = {
            navController.navigate(PROFILE_DETAILS_NAV_ROUTE)
        })
    }
    composable(PROFILE_DETAILS_NAV_ROUTE) {
        ProfileDetailsScreen()
    }
}

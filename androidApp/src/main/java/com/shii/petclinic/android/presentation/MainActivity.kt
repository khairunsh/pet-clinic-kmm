package com.shii.petclinic.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.shii.petclinic.android.MyApplicationTheme
import com.shii.petclinic.android.presentation.utils.NavigationRouteConstant.BOOKING_DETAILS_NAV_ROUTE
import com.shii.petclinic.android.presentation.utils.NavigationRouteConstant.BOOKING_NAV_GRAPH_ROUTE
import com.shii.petclinic.android.presentation.utils.NavigationRouteConstant.HOME_DETAILS_NAV_ROUTE
import com.shii.petclinic.android.presentation.utils.NavigationRouteConstant.HOME_NAV_GRAPH_ROUTE
import com.shii.petclinic.android.presentation.utils.NavigationRouteConstant.LOGIN_NAV_GRAPH_ROUTE
import com.shii.petclinic.android.presentation.utils.NavigationRouteConstant.MAIN_NAV_GRAPH_ROUTE
import com.shii.petclinic.android.presentation.utils.NavigationRouteConstant.PROFILE_DETAILS_NAV_ROUTE
import com.shii.petclinic.android.presentation.utils.NavigationRouteConstant.PROFILE_NAV_GRAPH_ROUTE
import com.shii.petclinic.android.utils.Preferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme() {
                MyApp()
            }
        }
    }

    @Composable
    fun MyApp() {
        val bottomNavItems = listOf(
            BottomNavItem.Home,
            BottomNavItem.Booking,
            BottomNavItem.Profile,
        )
        var selectedBottomNavItem by remember { mutableStateOf(bottomNavItems.first()) }

        var currentDestinationRoute by remember { mutableStateOf("") }
        var currentDestinationTitle by remember { mutableStateOf("") }

        val currentDestinationIsNotBottomNavItem = currentDestinationRoute !in listOf(
            BottomNavItem.Home.route,
            BottomNavItem.Booking.route,
            BottomNavItem.Profile.route,
        )

        val navController = rememberNavController()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentDestinationRoute = destination.route ?: ""

            val selectedBottomNavItemRoute =
                bottomNavItems.find { it.route == currentDestinationRoute }
            selectedBottomNavItemRoute?.let {
                selectedBottomNavItem = it

                currentDestinationTitle = it.title
            }

            when (destination.route) {
                HOME_DETAILS_NAV_ROUTE -> {
                    currentDestinationTitle = "Home Details"
                }
                BOOKING_DETAILS_NAV_ROUTE -> {
                    currentDestinationTitle = "Booking Details"
                }
                PROFILE_DETAILS_NAV_ROUTE -> {
                    currentDestinationTitle = "Profile Details"
                }
            }
        }

        val preferences = Preferences(LocalContext.current)
        val isLoggedIn = remember { mutableStateOf(preferences.get("isLoggedIn", false)) }

        Scaffold(
            topBar = {
                if (isLoggedIn.value) {
                    TopAppBar(
                        title = currentDestinationTitle,
                        showBackButton = currentDestinationIsNotBottomNavItem, // pass boolean value to TopAppBar
                        onBackClick = { navController.popBackStack() }, // handle back button click
                    )
                }
            },
            bottomBar = {
                if (isLoggedIn.value && !currentDestinationIsNotBottomNavItem) {
                    BottomNav(
                        navController = navController,
                        bottomNavItems = bottomNavItems,
                        selectedItem = selectedBottomNavItem,
                    )
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = if (isLoggedIn.value) MAIN_NAV_GRAPH_ROUTE else LOGIN_NAV_GRAPH_ROUTE,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(LOGIN_NAV_GRAPH_ROUTE) {
                    LoginScreen(LocalContext.current) {
                        preferences.set("isLoggedIn", true)

                        isLoggedIn.value = true
                    }
                }
                navigation(startDestination = HOME_NAV_GRAPH_ROUTE, route = MAIN_NAV_GRAPH_ROUTE) {
                    navigation(
                        startDestination = BottomNavItem.Home.route,
                        route = HOME_NAV_GRAPH_ROUTE,
                    ) {
                        homeNavGraph(navController = navController)
                    }
                    navigation(
                        startDestination = BottomNavItem.Booking.route,
                        route = BOOKING_NAV_GRAPH_ROUTE,
                    ) {
                        bookingNavGraph(navController = navController)
                    }
                    navigation(
                        startDestination = BottomNavItem.Profile.route,
                        route = PROFILE_NAV_GRAPH_ROUTE,
                    ) {
                        profileNavGraph(navController = navController)
                    }
                }
            }
        }
    }

    @Composable
    fun TopAppBar(
        title: String,
        showBackButton: Boolean = false, // default to false
        onBackClick: () -> Unit = {}, // default to empty click handler
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colors.surface),
        ) {
            Card(
                elevation = 4.dp,
                backgroundColor = MaterialTheme.colors.surface,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = if (showBackButton) 0.dp else 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (showBackButton) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colors.primary,
                            )
                        }
                    }
                    Text(
                        text = title,
                        color = MaterialTheme.colors.primary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }

    @Composable
    fun BottomNav(
        navController: NavController,
        bottomNavItems: List<BottomNavItem>,
        selectedItem: BottomNavItem,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface),
        ) {
            Column {
                Divider(
                    color = Color.Gray,
                    thickness = 0.5.dp,
                    modifier = Modifier.fillMaxWidth(),
                )
                BottomNavigation(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                ) {
                    bottomNavItems.forEach { bottomNavItem ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    bottomNavItem.icon,
                                    contentDescription = bottomNavItem.title,
                                )
                            },
                            label = { Text(bottomNavItem.title) },
                            selected = selectedItem == bottomNavItem,
                            selectedContentColor = MaterialTheme.colors.primary,
                            unselectedContentColor = Color.Gray,
                            onClick = {
                                navController.navigate(bottomNavItem.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when navigating back
                                    restoreState = true
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "Home")
    object Booking : BottomNavItem("booking", Icons.Filled.Email, "Booking")
    object Profile : BottomNavItem("profile", Icons.Filled.Person, "Profile")
}

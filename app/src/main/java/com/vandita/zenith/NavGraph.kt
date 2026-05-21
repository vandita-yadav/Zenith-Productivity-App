package com.vandita.zenith

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun ZenithNavigation()
{

    var completedSessions by remember { mutableStateOf<List<FocusSession>>(emptyList()) }
    var showAbout by remember { mutableStateOf(false) }
    val navController = rememberNavController()

    data class BottomNavItem(val title: String, val icon: ImageVector, val route: String)

    val items = listOf(

        BottomNavItem("Home", Icons.Default.Home, `Screen`.Dashboard.route),

        BottomNavItem("Focus Session", Icons.Default.Lock, `Screen`.ZenMode.route),

        BottomNavItem("History", Icons.Default.DateRange, `Screen`.History.route)
    )

    Scaffold(

        bottomBar = {

            NavigationBar(
                containerColor = CardBackground
            ) {

                val navBackStackEntry by navController
                    .currentBackStackEntryAsState()

                val currentRoute =
                    navBackStackEntry?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(

                        selected = currentRoute == item.route,

                        colors = NavigationBarItemDefaults.colors(

                            selectedIconColor = DeepGreen,
                            selectedTextColor = DeepGreen,
                            indicatorColor = CreamBackground,

                            unselectedIconColor = androidx.compose.ui.graphics.Color.Black,
                            unselectedTextColor = androidx.compose.ui.graphics.Color.Black
                        ),

                        onClick = {

                            navController.navigate(item.route)
                        },

                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },

                        label = {
                            Text(item.title)
                        }
                    )
                }
            }
        }

    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = `Screen`.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(`Screen`.Dashboard.route) {
                DashboardScreen(navController = navController)
            }

            composable(Screen.History.route) {
                HistoryScreen(completedSessions = completedSessions, navController = navController)
            }

            composable(Screen.ZenMode.route) {
                ZenModeScreen(
                    completedSessions = completedSessions,
                    onSessionAdded = { session ->
                        completedSessions = completedSessions + session
                    },
                    navController = navController
                )
            }

            composable(Screen.About.route) {
                AboutScreen(onBackClick = { navController.popBackStack() })
            }
        }
    }
}
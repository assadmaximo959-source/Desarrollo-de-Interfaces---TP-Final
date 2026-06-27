package com.example.dditpgrupal

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dditpgrupal.data.dummyCourseList
import com.example.dditpgrupal.sealedclass.navigationRouteList
import com.example.dditpgrupal.ui.components.CourseMenu
import com.example.dditpgrupal.ui.screens.CourseScreen
import com.example.dditpgrupal.ui.screens.HomeScreen
import com.example.dditpgrupal.ui.screens.MessagesScreen
import com.example.dditpgrupal.ui.screens.ProfileScreen
import com.example.dditpgrupal.ui.theme.DDITPGrupalTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DDITPGrupalTheme {
                AppNavigation()
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val mainRoutes = navigationRouteList.map { it.route }
    val showBottomBar = currentRoute in mainRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    navigationRouteList.forEach { route ->
                        NavigationBarItem(
                            icon = { Icon(route.icon, contentDescription = route.title) },
                            label = { Text(text = route.title) },
                            selected = currentRoute == route.route,
                            onClick = {
                                if (currentRoute != route.route) {
                                    navController.navigate(route.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = "home",
            ) {
                composable("home") {
                    HomeScreen()
                }
                composable("courses") {
                    CourseScreen(
                        onCourseClick = { index ->
                            navController.navigate("course/$index")
                        },
                    )
                }
                composable(
                    route = "course/{courseIndex}",
                    arguments = listOf(navArgument("courseIndex") { type = NavType.IntType }),
                ) { backStackEntry ->
                    val courseIndex = backStackEntry.arguments?.getInt("courseIndex") ?: 0
                    CourseMenu(
                        course = dummyCourseList[courseIndex],
                        onBackClick = { navController.popBackStack() },
                    )
                }
                composable("messages") {
                    MessagesScreen()
                }
                composable("profile") {
                    ProfileScreen()
                }
            }
        }
    }
}

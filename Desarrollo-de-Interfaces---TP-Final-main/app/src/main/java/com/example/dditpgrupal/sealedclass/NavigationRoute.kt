package com.example.dditpgrupal.sealedclass

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

val navigationRouteList =
    listOf(
        NavigationRoute.Home,
        NavigationRoute.Courses,
        NavigationRoute.Messages,
        NavigationRoute.ProfileInfo,
    )

sealed class NavigationRoute(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    object Home : NavigationRoute("home", "Inicio", Icons.Filled.Home)

    object Courses : NavigationRoute("courses", "Materias", Icons.Filled.Book)

    object Messages : NavigationRoute("messages", "Mensajes", Icons.Filled.Email)

    object ProfileInfo : NavigationRoute("profile", "Perfil", Icons.Filled.Person)
}

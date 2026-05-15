package com.example.nammavastra.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Trends : Screen("trends", "Trends", Icons.Default.TrendingUp)
    object Gallery : Screen("gallery", "Gallery", Icons.Default.PhotoLibrary)
    object Calculator : Screen("calculator", "Calculator", Icons.Default.Calculate)
    object Story : Screen("story", "Story", Icons.Default.HistoryEdu)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Trends,
    Screen.Gallery,
    Screen.Calculator,
    Screen.Story
)

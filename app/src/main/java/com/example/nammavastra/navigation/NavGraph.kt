package com.example.nammavastra.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nammavastra.screens.*

@Composable
fun NavGraph() {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(rootNavController)
        }
        composable("main_container") {
            MainContainer()
        }
    }
}

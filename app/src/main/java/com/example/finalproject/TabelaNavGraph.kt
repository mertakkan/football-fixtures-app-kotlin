package com.example.finalproject

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.finalproject.ui.screen.HomeScreen
import com.example.finalproject.ui.screen.LoginScreen

@Composable
fun TabelaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable(route = "login") {
            LoginScreen(
                navigateHome = { navController.navigate("home") },
            )
        }
        composable(route = "home") {
            HomeScreen(
                navigateProfile = { navController.navigate("profile") },
            )
        }
    }
}
package com.example.finalproject

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.finalproject.ui.screen.HomeScreen
import com.example.finalproject.ui.screen.LoginScreen
import com.example.finalproject.ui.screen.ProfileScreen

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
                navigateHome = { navController.navigate("home/$it") },
            )
        }
        composable(
            route = "home/{username}",
            arguments = listOf(navArgument("username") {
                type = NavType.StringType
            })
        ) {
            it.arguments?.let { it1 ->
                HomeScreen(
                    currentUsername = it1.getString("username") as String,
                    navigateProfile = { navController.navigate("profile/${it1.getString("username") as String}") },
                )
            }
        }
        composable(
            route = "profile/{username}",
            arguments = listOf(navArgument("username") {
                type = NavType.StringType
            })
        ) {
            ProfileScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}
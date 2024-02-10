package com.example.random_users.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.random_users.main.MainScreen
import com.example.random_users.user.UserScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = main) {
            composable(route = main) {
                MainScreen(navController)
            }
            composable(
                route = user,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val arguments = requireNotNull(it.arguments)
                val id = arguments.getInt("id")

                UserScreen(id = id, navController = navController)
            }
        }
    }
}
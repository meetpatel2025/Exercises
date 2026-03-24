package com.training.formapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.training.formapp.screens.UserListScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list"){
        composable("list") {
            UserListScreen(navController)
        }
        composable("create_profile")  {
            UserListScreen(navController)
        }
    }
}

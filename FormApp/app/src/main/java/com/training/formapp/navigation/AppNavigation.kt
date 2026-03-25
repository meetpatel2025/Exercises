package com.training.formapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.training.formapp.screens.AddEditScreen
import com.training.formapp.screens.HomeScreen
import com.training.formapp.viewmodel.UserViewModel

@Composable
fun AppNavigation(viewModel: UserViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {

        composable("list") {
            HomeScreen(navController, viewModel)
        }

        composable("add") {
            AddEditScreen(navController, viewModel, null)
        }

        composable("edit/{userId}") { backStack ->
            val id = backStack.arguments?.getString("userId")?.toInt()
            AddEditScreen(navController, viewModel, id)
        }
    }
}
package com.example.doyouknowit.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.doyouknowit.ui.screens.CategorySelectorScreen
import com.example.doyouknowit.ui.screens.QuizScreen
import com.example.doyouknowit.ui.screens.LoginScreen
import com.example.doyouknowit.ui.screens.RegistrationScreen
import com.example.doyouknowit.ui.viewmodel.UserViewModel

@Composable
fun AppNavigation(userViewModel: UserViewModel, startDestination: String = "login") {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController, userViewModel) }
        composable("register") { RegistrationScreen(navController, userViewModel) }
        composable("category_selector") {
            CategorySelectorScreen(navController, userViewModel, onCategorySelected = { category, highScores ->
                navController.navigate("quiz_screen/$category") {
                    navController.currentBackStackEntry?.arguments?.putSerializable("highScores", HashMap(highScores))
                }
            })
        }
        composable("quiz_screen/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val highScores = backStackEntry.arguments?.getSerializable("highScores") as? Map<String, Int> ?: emptyMap()
            QuizScreen(navController, userViewModel, category, highScores, onResult = { result -> /* handle result */ })
        }
    }
}
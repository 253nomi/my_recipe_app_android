package com.example.myrecipeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.myrecipeapp.repository.MainRepositoryImpl
import com.example.myrecipeapp.viewmodels.MainViewModelCompose

@Composable
fun ComposeRecipeApp(navController: NavHostController, modifier: Modifier) {
    val repository = remember {
        MainRepositoryImpl(apiService = receipeService)
    }

    val recipeViewModel = MainViewModelCompose(_repository = repository)

    NavHost(navController = navController, startDestination = Screen.RecipeScreen.route) {
        composable(route = Screen.RecipeScreen.route) {
            RecipeScreen(
                modifier = modifier,
                viewModel = recipeViewModel,
                navigateToDetails = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("cat", it)
                    navController.navigate(Screen.DetailsScreen.route)
                }
            )
        }

        composable(route = Screen.DetailsScreen.route) {
            val category =
                navController.previousBackStackEntry?.savedStateHandle?.get<Category>("cat")
                    ?: Category(
                        "", "", "", ""
                    )
            CategoryDetailsCScreen(category, modifier)
        }

    }
}
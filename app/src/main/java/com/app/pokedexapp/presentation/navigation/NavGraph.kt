package com.app.pokedexapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.pokedexapp.presentation.screens.sudoku.SudokuScreen

sealed class Screen(
    val route: String,
) {
    object Sudoku : Screen("sudoku")
}

@Composable
fun PokemonNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Sudoku.route,
        modifier = modifier,
    ) {
        composable(Screen.Sudoku.route) {
            SudokuScreen()
        }
    }
}

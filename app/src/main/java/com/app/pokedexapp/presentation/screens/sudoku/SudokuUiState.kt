package com.app.pokedexapp.presentation.screens.sudoku

import com.app.pokedexapp.domain.model.SudokuGame

data class SudokuUiState(
    val game: SudokuGame? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null,
    val isGameDone: Boolean = false,
)

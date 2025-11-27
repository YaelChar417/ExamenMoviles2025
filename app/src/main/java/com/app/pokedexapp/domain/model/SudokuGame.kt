package com.app.pokedexapp.domain.model

data class SudokuGame(
    val board: List<List<SudokuCell>>,
    val difficulty: String,
    val size: Int,
    val isSolved: Boolean = false,
)

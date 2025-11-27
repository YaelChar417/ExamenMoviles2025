package com.app.pokedexapp.domain.model

data class SudokuCell(
    val row: Int,
    val column: Int,
    val currentVal: Int? = null,
    val correctVal: Int,
    val isFromApi: Boolean,
    val isError: Boolean = false,
)
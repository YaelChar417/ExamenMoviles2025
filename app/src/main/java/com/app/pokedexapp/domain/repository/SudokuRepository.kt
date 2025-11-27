package com.app.pokedexapp.domain.repository

import com.app.pokedexapp.domain.model.SudokuGame

interface SudokuRepository {
    // Crea un nuevo juego de sudoku, con el tamaño y dificultad, y devuelve un resultado
    // de error o exito con el juego creado
    suspend fun getNewGame(
        size: Int,
        difficulty: String,
    ): Result<SudokuGame>

    // Guarda un juego
    suspend fun saveGame(game: SudokuGame)

    // Carga el juego guardado
    suspend fun loadGame(): SudokuGame?

    // Verifica si se tienen juegos guardados
    suspend fun hasSavedGame(): Boolean
}
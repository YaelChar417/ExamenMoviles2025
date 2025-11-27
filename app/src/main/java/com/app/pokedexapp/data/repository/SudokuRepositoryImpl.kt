package com.app.pokedexapp.data.repository

import com.app.pokedexapp.data.local.preferences.SessionPreferences
import com.app.pokedexapp.data.mapper.toDomain
import com.app.pokedexapp.data.remote.api.SudokuApi
import com.app.pokedexapp.domain.model.SudokuGame
import com.app.pokedexapp.domain.repository.SudokuRepository
import javax.inject.Inject

class SudokuRepositoryImpl
    @Inject
    constructor(
        private val api: SudokuApi,
        private val preferences: SessionPreferences,
    ) : SudokuRepository {
        override suspend fun getNewGame(
            size: Int,
            difficulty: String,
        ): Result<SudokuGame> =
            try {
                val response =
                    api.getSudoku(
                        width = size,
                        height = size,
                        difficulty = difficulty,
                    )

                val game =
                    response.toDomain(
                        difficulty = difficulty,
                        size = size,
                    )

                Result.success(game)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun saveGame(game: SudokuGame) {
            preferences.saveGame(game)
        }

        override suspend fun loadGame(): SudokuGame? = preferences.getSavedGame()

        override suspend fun hasSavedGame(): Boolean = preferences.hasSavedGame()
    }

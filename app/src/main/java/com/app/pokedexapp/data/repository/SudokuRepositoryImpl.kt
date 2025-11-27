package com.app.pokedexapp.data.repository

import retrofit2.HttpException
import com.app.pokedexapp.data.local.preferences.SessionPreferences
import com.app.pokedexapp.data.mapper.toDomain
import com.app.pokedexapp.data.remote.api.SudokuApi
import com.app.pokedexapp.domain.model.SudokuGame
import com.app.pokedexapp.domain.repository.SudokuRepository
import java.io.IOException
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
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    400 -> "No se pudo generar el tablero con esta configuración ($size x $size - $difficulty). Intenta otra dificultad."
                    401, 403 -> "Error de autenticación. Verifica tu API Key."
                    404 -> "Servicio no encontrado."
                    500, 502, 503, 504 -> "El servidor de Sudoku está fallando o tardando mucho. Intenta de nuevo en unos segundos."
                    else -> "Error del servidor: ${e.code()}"
                }
                Result.failure(Exception(message))

            } catch (e: IOException) {
                Result.failure(Exception("No hay conexión a internet. Revisa tu red."))

            } catch (e: Exception) {
                Result.failure(Exception("Ocurrió un error inesperado: ${e.localizedMessage}"))
            }

    override suspend fun saveGame(game: SudokuGame) {
            preferences.saveGame(game)
        }

        override suspend fun loadGame(): SudokuGame? = preferences.getSavedGame()

        override suspend fun hasSavedGame(): Boolean = preferences.hasSavedGame()
    }

package com.app.pokedexapp.domain.usecase
import com.app.pokedexapp.domain.common.Result
import com.app.pokedexapp.domain.model.SudokuGame
import com.app.pokedexapp.domain.repository.SudokuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadGameUseCase
    @Inject
    constructor(
        private val repository: SudokuRepository,
    ) {
        operator fun invoke(): Flow<Result<SudokuGame>> =
            flow {
                try {
                    emit(Result.Loading)

                    val game = repository.loadGame()

                    if (game != null) {
                        emit(Result.Success(game))
                    } else {
                        emit(Result.Error(Exception("No hay un juego guardado")))
                    }
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }

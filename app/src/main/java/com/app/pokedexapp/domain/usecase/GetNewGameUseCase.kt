package com.app.pokedexapp.domain.usecase

import com.app.pokedexapp.domain.common.Result
import com.app.pokedexapp.domain.model.SudokuGame
import com.app.pokedexapp.domain.repository.SudokuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewGameUseCase
    @Inject
    constructor(
        private val repository: SudokuRepository,
    ) {
        suspend operator fun invoke(
            size: Int,
            difficulty: String,
        ): Flow<Result<SudokuGame>> =
            flow {
                try {
                    emit(Result.Loading)

                    val result =
                        repository.getNewGame(
                            size = size,
                            difficulty = difficulty,
                        )

                    result
                        .onSuccess { game ->
                            emit(Result.Success(game))
                        }.onFailure { error ->
                            emit(Result.Error(error))
                        }
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }

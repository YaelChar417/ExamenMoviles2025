package com.app.pokedexapp.domain.usecase

import com.app.pokedexapp.domain.common.Result
import com.app.pokedexapp.domain.repository.SudokuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckSaveGameUseCase
    @Inject
    constructor(
        private val repository: SudokuRepository,
    ) {
        operator fun invoke(): Flow<Result<Boolean>> =
            flow {
                try {
                    emit(Result.Loading)

                    val hasGame = repository.hasSavedGame()

                    emit(Result.Success(hasGame))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }

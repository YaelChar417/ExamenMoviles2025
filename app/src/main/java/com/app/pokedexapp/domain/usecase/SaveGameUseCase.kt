package com.app.pokedexapp.domain.usecase

import com.app.pokedexapp.domain.common.Result
import com.app.pokedexapp.domain.model.SudokuGame
import com.app.pokedexapp.domain.repository.SudokuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveGameUseCase
    @Inject
    constructor(
        private val repository: SudokuRepository,
    ) {
        operator fun invoke(game: SudokuGame): Flow<Result<Unit>> =
            flow {
                try {
                    emit(Result.Loading)

                    repository.saveGame(game)

                    emit(Result.Success(Unit))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }

package com.app.pokedexapp.domain.usecase

import com.app.pokedexapp.domain.common.Result
import com.app.pokedexapp.domain.model.Pokemon
import com.app.pokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonListUseCase
    @Inject
    constructor(
        private val repository: PokemonRepository, // Inyectado por Hilt
    ) {
        operator fun invoke(): Flow<Result<List<Pokemon>>> =
            flow {
                try {
                    // Primer valor: Loading
                    emit(Result.Loading)

                    // Obtiene datos
                    val pokemonList = repository.getPokemonList()

                    // Segundo valor: Success con datos
                    emit(Result.Success(pokemonList))
                } catch (e: Exception) {
                    // O Error si algo falla
                    emit(Result.Error(e))
                }
            }
    }

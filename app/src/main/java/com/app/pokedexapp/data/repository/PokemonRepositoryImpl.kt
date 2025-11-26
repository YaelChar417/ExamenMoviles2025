package com.app.pokedexapp.data.repository

import com.app.pokedexapp.data.local.preferences.PokemonPreferences
import com.app.pokedexapp.data.mapper.toDomain
import com.app.pokedexapp.data.remote.api.PokemonApi
import com.app.pokedexapp.domain.model.Pokemon
import com.app.pokedexapp.domain.repository.PokemonRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl
    @Inject
    constructor(
        private val api: PokemonApi,
        private val preferences: PokemonPreferences,
    ) : PokemonRepository {
        override suspend fun getPokemonList(): List<Pokemon> {
            // Intentar obtener del caché primero
            preferences.getPokemonCache()?.let { cache ->
                if (preferences.isCacheValid()) {
                    return cache.pokemonList
                }
            }

            return try {
                // Si no hay caché o expiró, obtener de la API
                val response = api.getPokemonList()
                val pokemonList =
                    response.results.map { result ->
                        val id =
                            result.url
                                .split("/")
                                .dropLast(1)
                                .last()
                        api.getPokemon(id).toDomain()
                    }

                // Guardar en caché
                preferences.savePokemonList(
                    pokemonList = pokemonList,
                    offset = pokemonList.size,
                    totalCount = response.count,
                )

                pokemonList
            } catch (e: Exception) {
                // Si hay error, intentar usar caché aunque haya expirado
                preferences.getPokemonCache()?.let { cache ->
                    return cache.pokemonList
                } ?: throw e
            }
        }

        override suspend fun getPokemonById(id: String): Pokemon {
            // Obtener del cache primero
            preferences.getPokemonCache()?.let { cache ->
                if (preferences.isCacheValid()) {
                    cache.pokemonList.find { it.id == id }?.let { return it }
                }
            }

            return try {
                // si no esta en cache o expiro, obtenerlo de la API
                api.getPokemon(id).toDomain()
            } catch (e: Exception) {
                // Si hay error, bucar en cache aunque esté expirado
                preferences.getPokemonCache()?.let { cache ->
                    cache.pokemonList.find { it.id == id }
                } ?: throw e
            }
        }
    }

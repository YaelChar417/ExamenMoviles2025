package com.app.pokedexapp.domain.model

data class Pokemon(
    val id: String,
    val name: String,
    val imageUrl: String,
    val weight: Int,
    val height: Int,
    val types: List<String>,
) {
    companion object {
        fun getMockData(): List<Pokemon> =
            listOf(
                Pokemon(
                    id = "1",
                    name = "Bulbasaur",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    weight = 69,
                    height = 7,
                    types = listOf("Grass", "Poison"),
                ),
                Pokemon(
                    id = "2",
                    name = "ivysaur",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
                    weight = 80,
                    height = 15,
                    types = listOf("Grass", "Poison"),
                ),
                Pokemon(
                    id = "3",
                    name = "Venusaur",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
                    weight = 120,
                    height = 30,
                    types = listOf("Grass", "Poison"),
                ),
                Pokemon(
                    id = "4",
                    name = "charmander",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                    weight = 35,
                    height = 15,
                    types = listOf("fire"),
                ),
                Pokemon(
                    id = "5",
                    name = "Charmelion",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png",
                    weight = 55,
                    height = 28,
                    types = listOf("fire"),
                ),
            )
    }
}

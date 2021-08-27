package com.tahadroid.pokemon.model

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Pokemon>
)
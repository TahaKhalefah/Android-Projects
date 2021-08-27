package com.tahadroid.pokemon.repository

import com.tahadroid.pokemon.db.PokemonDao
import com.tahadroid.pokemon.model.Pokemon
import com.tahadroid.pokemon.network.PokemonApiService
import javax.inject.Inject

class PokemonRepository
@Inject
constructor(
    val pokemonDao: PokemonDao,
    val apiService: PokemonApiService
) {
    fun getPokemons() = apiService.getPokemons()

    fun insertPokemon(pokemon: Pokemon) =pokemonDao.insertPokemon(pokemon)

    fun deletePokemon(pokemonName: String) =pokemonDao.deletePokemon(pokemonName)

    fun deleteAllPokemons() =pokemonDao.deleteAllPokemons()

    fun getAllPokemons() =pokemonDao.getAllPokemon()
}
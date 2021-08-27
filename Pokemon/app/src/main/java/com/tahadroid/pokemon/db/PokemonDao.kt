package com.tahadroid.pokemon.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tahadroid.pokemon.model.Pokemon

@Dao
interface PokemonDao {
    @Insert
    fun insertPokemon(pokemon: Pokemon)

    @Query("delete from Pokemon where name =:pokemonName")
    fun deletePokemon(pokemonName: String)

    @Query("delete  from pokemon")
    fun deleteAllPokemons()

    @Query("select * from Pokemon")
    fun getAllPokemon(): LiveData<List<Pokemon>>
}
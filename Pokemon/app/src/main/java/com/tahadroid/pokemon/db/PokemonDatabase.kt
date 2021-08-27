package com.tahadroid.pokemon.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tahadroid.pokemon.model.Pokemon

@Database(
    entities = [Pokemon::class],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
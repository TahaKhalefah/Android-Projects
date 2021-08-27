package com.tahadroid.pokemon.di

import android.content.Context
import androidx.room.Room
import com.tahadroid.pokemon.common.Constans.DATABASE_NAME
import com.tahadroid.pokemon.db.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providePokemonDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        PokemonDatabase::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun providePokemonDao(db: PokemonDatabase) = db.pokemonDao()
}
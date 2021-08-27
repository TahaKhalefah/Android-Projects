package com.tahadroid.pokemon.network

import com.tahadroid.pokemon.model.PokemonResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface PokemonApiService {
    @GET("pokemon")
    fun getPokemons():Observable<PokemonResponse>
}
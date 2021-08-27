package com.tahadroid.pokemon.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tahadroid.pokemon.model.Pokemon
import com.tahadroid.pokemon.repository.PokemonRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class PokemonViewModel
@ViewModelInject
constructor(
    val repo: PokemonRepository
) : ViewModel() {
    private val _pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    fun getPokemons() {
        repo.getPokemons()
            .subscribeOn(Schedulers.io())
            .map { response ->
                val list = response.results
                for (pokemon in list) {
                    val url = pokemon.url
                    // to spilt the index from url
                    //first droplast then get last
                    val pokemonIndex: String = url.split("/".toRegex()).dropLast(1).last()
                    pokemon.url =
                        "https://pokeres.bastionbot.org/images/pokemon/${pokemonIndex}.png"
                }
                list
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _pokemonList.value = it }
    }

    fun insertPokemon(pokemon: Pokemon) = repo.insertPokemon(pokemon)

    fun deletePokemon(pokemonName: String) = repo.deletePokemon(pokemonName)

    fun deleteAll() = repo.deleteAllPokemons()

    fun getAll() = repo.getAllPokemons()
}


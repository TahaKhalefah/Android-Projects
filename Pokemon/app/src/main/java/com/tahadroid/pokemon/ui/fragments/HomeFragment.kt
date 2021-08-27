package com.tahadroid.pokemon.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tahadroid.pokemon.R
import com.tahadroid.pokemon.adapters.PokemonAdapter
import com.tahadroid.pokemon.model.Pokemon
import com.tahadroid.pokemon.ui.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment :Fragment(R.layout.fragment_home){
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var viewModel: PokemonViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)

        initRecyclerView()
        observeData()
        setUpItemTouchHelper()
        viewModel.getPokemons()
    }

    private fun setUpItemTouchHelper() {
        val simpleCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                     recyclerView: RecyclerView,
                     viewHolder: RecyclerView.ViewHolder,
                     target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val swipedPokemonPosition = viewHolder.adapterPosition
                    val pokemon: Pokemon? = pokemonAdapter.getPokemonAt(swipedPokemonPosition)
                    if (pokemon != null) {
                        viewModel.insertPokemon(pokemon)
                    }
                    pokemonAdapter.notifyDataSetChanged()
                    Toast.makeText(context, "Pokemon added to favorites.", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(home_rv)
    }

    private fun observeData() {
        viewModel.pokemonList.observe(viewLifecycleOwner, Observer {
            pokemonAdapter.swapData(it)
        })
    }

    private fun initRecyclerView() {
        pokemonAdapter =PokemonAdapter{
                _, _, _ ->
        }

        home_rv.apply {
            adapter=pokemonAdapter
        }
    }
}
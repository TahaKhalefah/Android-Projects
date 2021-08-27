package com.tahadroid.pokemon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.pokemon.R
import com.tahadroid.pokemon.model.Pokemon
import kotlinx.android.synthetic.main.layout_list_item.view.*


class PokemonAdapter(val listener: (View, Pokemon, Int) -> Unit) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    private var data: List<Pokemon> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<Pokemon>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getPokemonAt(position: Int): Pokemon? {
        return data.get(position)
    }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Pokemon) = with(itemView) {
            pokemonName.text = item.name
            Glide.with(context).load(item.url).into(pokemonImage)

            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}
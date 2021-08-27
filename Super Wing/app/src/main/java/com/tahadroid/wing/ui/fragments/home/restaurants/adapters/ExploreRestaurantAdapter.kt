package com.tahadroid.wing.ui.fragments.home.restaurants.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.wing.R
import com.tahadroid.wing.models.Category
import kotlinx.android.synthetic.main.layout_explore_item.view.*


class ExploreRestaurantAdapter(val listener: (View, Category, Int) -> Unit) :
    RecyclerView.Adapter<ExploreRestaurantAdapter.ProductViewHolder>() {
    private var data: List<Category> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_explore_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(data[position])

    }

    fun swapData(data: List<Category>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Category) = with(itemView) {

            Glide.with(context).load(item.icon).into(imageView)
            foodTextView.text = item.name
            placesNumberTextView.text = item.numPlaces.toString() +" Places"
            if (adapterPosition == 0) {
                backgroundViewLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        getContext(),
                        R.color.red
                    )
                )
            } else if (adapterPosition == 1) {
                backgroundViewLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        getContext(),
                        R.color.darkGreen
                    )
                )
            } else if (adapterPosition == 2) {
                backgroundViewLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        getContext(),
                        R.color.yellow
                    )
                )
            } else if (adapterPosition == 3) {
                backgroundViewLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        getContext(),
                        R.color.lightGray
                    )
                )
            } else if (adapterPosition == 4) {
                backgroundViewLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        getContext(),
                        R.color.colorAccent
                    )
                )
            }

            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}
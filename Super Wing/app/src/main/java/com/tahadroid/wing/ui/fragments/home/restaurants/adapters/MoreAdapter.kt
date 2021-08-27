package com.tahadroid.wing.ui.fragments.home.restaurants.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.wing.R
import com.tahadroid.wing.models.More
import kotlinx.android.synthetic.main.layout_explore_item.view.*


class MoreAdapter(val listener: (View, More, Int) -> Unit) :
    RecyclerView.Adapter<MoreAdapter.ProductViewHolder>() {
    private var data: List<More> = ArrayList()

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
    fun swapData(data: List<More>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: More) = with(itemView) {

            Glide.with(context).load(item.imagePath).into(imageView)
            foodTextView.text=item.foodName
            placesNumberTextView.text=item.descriptionShop
            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}
package com.tahadroid.wing.ui.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.wing.R
import com.tahadroid.wing.models.Shop
import kotlinx.android.synthetic.main.layout_home_item.view.*


class HomeAdapter(val listener: (View, Shop, Int) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.ProductViewHolder>() {
    private var data: List<Shop> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_home_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(data[position])

    }

    fun swapData(data: List<Shop>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Shop) = with(itemView) {
            Glide.with(context).load(item.image).into(homeImageView)
            nameTextView.text = item.shopName
            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}
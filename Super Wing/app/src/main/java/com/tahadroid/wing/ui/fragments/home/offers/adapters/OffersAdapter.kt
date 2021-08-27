package com.tahadroid.wing.ui.fragments.home.offers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.wing.R
import com.tahadroid.wing.models.Menu
import kotlinx.android.synthetic.main.layout_offer_item.view.*


class OffersAdapter(val listener: (View, Menu, Int) -> Unit) :
    RecyclerView.Adapter<OffersAdapter.ProductViewHolder>() {
    private var data: List<Menu> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_offer_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(data[position])

    }

    fun swapData(data: List<Menu>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Menu) = with(itemView) {
            nameTextView.text = item.name
            discountTextView.text = item.discount.toString() +" %"
            Glide.with(context).load(item.image).into(orderImageView)
            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}
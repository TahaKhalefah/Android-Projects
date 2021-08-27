package com.tahadroid.wing.ui.fragments.home.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.wing.R
import com.tahadroid.wing.models.Shop
import kotlinx.android.synthetic.main.layout_search_item.view.*
import java.util.*
import kotlin.collections.ArrayList


class SearchActivityAdapter(val listener: (View, Shop, Int) -> Unit) :
    RecyclerView.Adapter<SearchActivityAdapter.ProductViewHolder>() {
    private var data: ArrayList<Shop> = ArrayList()
    private var copyOfContactsList: ArrayList<Shop> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_search_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(data[position])

    }

    fun swapData(data: ArrayList<Shop>) {
        this.data = data
        copyOfContactsList.addAll(data)
        notifyDataSetChanged()
    }
    fun filter(text: String) {
        data.clear()
        if (text.isEmpty()) {
            data.addAll(copyOfContactsList)
        } else {
            for (item in copyOfContactsList) {
                if (item.shopName!!.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                    data.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Shop) = with(itemView) {

            Glide.with(context).load(item.image).into(imageView1)
            textView1.text = item.shopName

            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}
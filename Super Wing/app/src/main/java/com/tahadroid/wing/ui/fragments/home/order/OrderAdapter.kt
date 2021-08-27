package com.tahadroid.wing.ui.fragments.home.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.wing.R
import com.tahadroid.wing.models.Menu
import kotlinx.android.synthetic.main.layout_menu_item.view.*

class OrderAdapter(val listener: (View, Menu, Int) -> Unit) :
    RecyclerView.Adapter<OrderAdapter.ProductViewHolder>() {
    private var data: List<Menu> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_menu_item, parent, false)
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
            Glide.with(context).load(item.image).into(imgFoodImageView)
            priceTextView.text=item.price.toString()
            titleTextView.text=item.name
            happyTextView.text=item.shopName
            inStockTextView.text=when(item.inStock){
                true ->{
                    "in Stock"
                }
                else ->{
                    "Out Of Stock"
                }

            }
            if(item.inStock==false){
                plusView.visibility=View.GONE
            }else {
                plusView.setOnClickListener {
                    listener.invoke(it, item, adapterPosition)
                }
            }
        }
    }
}
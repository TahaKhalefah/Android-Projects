package com.tahadroid.wing.ui.fragments.home.homemade

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.wing.R
import com.tahadroid.wing.models.Menu
import kotlinx.android.synthetic.main.layout_home_made_item.view.*

class HomeMadeAdapter(val listener: (View, Menu, Int) -> Unit) :
    RecyclerView.Adapter<HomeMadeAdapter.ProductViewHolder>() {
    private var data: List<Menu> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_home_made_item, parent, false)
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
            nameDishTextView.text = item.name
            descriptionDishTextView.text=item.description
            Glide.with(context).load(item.image).into(dishImageView)
            if(item.discount!! > 0){
                view2.visibility=View.VISIBLE
                discountTextView.text = item.discount.toString()+"%"
            }else{
                view2.visibility=View.GONE
                discountTextView.visibility=View.GONE
            }
            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}
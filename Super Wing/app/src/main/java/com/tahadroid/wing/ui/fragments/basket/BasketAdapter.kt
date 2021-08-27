package com.tahadroid.wing.ui.fragments.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.wing.R
import com.tahadroid.wing.data.local.OrderDatabase
import com.tahadroid.wing.models.Order
import kotlinx.android.synthetic.main.layout_basket_item.view.*


class BasketAdapter(
    val listener: (View, Order, Int) -> Unit,
    val context: Context,
    val calculateOrderPrice: CalculateOrderPrice
) :
    RecyclerView.Adapter<BasketAdapter.ProductViewHolder>() {

    var price = 0.0
    private var data: List<Order> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_basket_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(data[position])

    }

    fun swapData(data: List<Order>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Order) = with(itemView) {
            Glide.with(context).load(item.image).into(imageview_rv_order)
            textViewOrderName.text = item.title
            textViewPlaceName.text = item.placeName
            textViewSize.text = item.size.toString()
            textViewQuantity.text = item.quantity.toString()
            price = (item.price * item.quantity).toDouble()
            textViewPrice.text = (price).toString()
            imageViewIncrease.setOnClickListener {
                item.quantity++
                textViewQuantity.text = item.quantity.toString()
                price = (item.price * item.quantity).toDouble()
                textViewPrice.text = (price).toString()
                calculateOrderPrice.calOrder()
                OrderDatabase.invoke(context).getOrderDoa().updateOrder(item)
            }
            imageViewDecrease.setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity--
                    textViewQuantity.text = item.quantity.toString()
                    price = (item.price * item.quantity).toDouble()
                    textViewPrice.text = (price).toString()
                    calculateOrderPrice.calOrder()
                    OrderDatabase.invoke(context).getOrderDoa().updateOrder(item)
                }
            }
            imageViewDelete.setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }

}
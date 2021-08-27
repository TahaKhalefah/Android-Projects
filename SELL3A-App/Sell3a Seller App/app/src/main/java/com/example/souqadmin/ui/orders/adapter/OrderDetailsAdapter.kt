package com.example.souqadmin.ui.orders.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqadmin.databinding.OrderDetailsItemRvBinding
import com.example.souqadmin.pojo.ItemResponse
import com.example.souqadmin.utils.ProuductData.product

class OrderDetailsAdapter(val listener: (View, ItemResponse, Int) -> Unit?, val context: Context) :
    RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {

    var data = mutableListOf<ItemResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OrderDetailsItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun changeData(newData: MutableList<ItemResponse>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: OrderDetailsItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemResponse) = with(itemView) {

            Glide.with(context)
                .load(item[0].image)
                .fitCenter()
                .into(binding.productImage)

            binding.productNameTv.text = item[0].title
            binding.productSizeTv.text = item[0].size[0].toString()
            binding.productColorTv.text = item[0].color[0].toString()
            binding.cost.text = item[0].price.toString()
            binding.quantityTv.text = item[0].quantity.toString()


        }
    }

}
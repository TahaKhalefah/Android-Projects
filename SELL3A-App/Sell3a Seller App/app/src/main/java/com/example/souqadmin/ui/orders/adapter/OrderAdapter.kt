package com.example.souqadmin.ui.orders.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.souqadmin.R
import com.example.souqadmin.databinding.OrderItemRvBinding
import com.example.souqadmin.pojo.Colors
import com.example.souqadmin.pojo.OrderResponseItem

class OrderAdapter(val listener: (View, OrderResponseItem, Int) -> Unit?) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private var data = listOf<OrderResponseItem>()

    fun changeData(newData: List<OrderResponseItem>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: OrderItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderResponseItem) = with(itemView) {
            var userInfo = item.userId.split("@")
            binding.orderNoTv.text = item.orderCode
            binding.userNameTv.text = userInfo[0]
            binding.costTv.text = item.totalPrice.toString()
            binding.dateTv.text = item.orderDate

            if(userInfo[1] != null){
                binding.userPhone.text = userInfo[1]
            } else{
                binding.userPhone.text = ""
            }
            Log.i("TAG", userInfo[1])

            binding.paymentTv.text = "Visa"

            binding.stateTv.text = item.orderState

            if (binding.stateTv.text == "Packing") {
                binding.stateTv.setBackgroundResource(R.color.receivedBlue)
            } else if (binding.stateTv.text == "Shipped") {
                binding.stateTv.setBackgroundResource(R.color.shippedOrange)
            }else if (binding.stateTv.text == "Delivered") {
                binding.stateTv.setBackgroundResource(R.color.deliveredGreen)
            }else  {
                binding.stateTv.setBackgroundResource(R.color.cancelledRed)
            }

            val tax = 40
            val total = item.totalPrice + tax
            binding.costTv.text = "\$${total.toInt()}"


            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OrderItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])
}

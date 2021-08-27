package com.tahadroid.wing.ui.fragments.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tahadroid.wing.R
import com.tahadroid.wing.models.ActivityCategory
import kotlinx.android.synthetic.main.layout_activity_item.view.*


class ActivityCategotyAdapter(val listener: (View, ActivityCategory, Int) -> Unit) :
    RecyclerView.Adapter<ActivityCategotyAdapter.ProductViewHolder>() {
    private var data: List<ActivityCategory> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_activity_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(data[position])

    }

    fun swapData(data: List<ActivityCategory>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ActivityCategory) = with(itemView) {
            textViewActivityCategory.text=item.activityCategoryName
            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}
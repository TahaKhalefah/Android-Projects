package com.tahadroid.wing.ui.fragments.home.homemade

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.wing.R
import com.tahadroid.wing.models.Section
import kotlinx.android.synthetic.main.layout_section_item.view.*

class SectionAdapter(val listener: (View, Section, Int) -> Unit) :
    RecyclerView.Adapter<SectionAdapter.ProductViewHolder>() {
    private var data: List<Section> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_section_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(data[position])
        if(position == 0)
            holder.itemView.performClick()
    }


    fun swapData(data: List<Section>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Section) = with(itemView) {
            typeNameTextView.text = item.sectionName

            Glide.with(context).load(item.sectionIcon).into(typesImageView)
            setOnClickListener {
                listener.invoke(it, item, adapterPosition)

            }
        }
    }
}
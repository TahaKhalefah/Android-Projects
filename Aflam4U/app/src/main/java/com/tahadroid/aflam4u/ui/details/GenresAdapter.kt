package com.tahadroid.aflam4u.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tahadroid.aflam4u.R
import com.tahadroid.aflam4u.pojo.details.GenresItem
import kotlinx.android.synthetic.main.layout_genres_item.view.*


class GenresAdapter(val listener: (View, GenresItem, Int) -> Unit) :
    RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    private var data: List<GenresItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        return GenresViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_genres_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<GenresItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class GenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: GenresItem) = with(itemView) {
            genres_item_tv.text=item.name
            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}
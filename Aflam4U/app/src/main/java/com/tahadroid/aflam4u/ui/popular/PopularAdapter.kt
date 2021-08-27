package com.tahadroid.aflam4u.ui.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.aflam4u.R
import com.tahadroid.aflam4u.pojo.popular.ResultsItem
import com.tahadroid.aflam4u.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.layout_popular_item.view.*


class PopularAdapter(val listener: (View, ResultsItem, Int) -> Unit) :
    RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    private var data: List<ResultsItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_popular_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<ResultsItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class PopularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ResultsItem) = with(itemView) {
            Glide.with(this).load("$IMAGE_BASE_URL${item.posterPath}")
                .into(poster_details_iv)
            title_tv.text = item.title
            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}
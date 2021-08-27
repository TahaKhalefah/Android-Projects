package com.tahadroid.bestrepo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tahadroid.bestrepo.databinding.LayoutRepoItemBinding
import com.tahadroid.bestrepo.model.Item


class RepoAdapter(val listener: (View, Item, Int) -> Unit) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    private var data: MutableList<Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            LayoutRepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: MutableList<Item>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getRepoAt(position: Int): Item? {
        return data.get(position)
    }

    inner class RepoViewHolder(var binding: LayoutRepoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) = with(itemView) {
            binding.nameTextView.text = item.name
            if(item.description.isNullOrBlank()  ){
                binding.descTextView.text = "N/A"
            }else{
                binding.descTextView.text = item.description
            }
            binding.numStarTextView.text = item.stargazers_count.toString()
            binding.avatarImageView.load(item.owner.avatar_url)
            binding.usernameTextView.text = item.owner.login
            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}
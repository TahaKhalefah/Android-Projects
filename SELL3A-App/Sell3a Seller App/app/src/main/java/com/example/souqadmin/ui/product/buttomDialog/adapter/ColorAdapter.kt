package com.example.souqadmin.ui.product.buttomDialog.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.souqadmin.databinding.FragmentSelectColorListDialogItemBinding

class ColorAdapter internal constructor() : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    var colors = arrayListOf<String>()
    var setOnItemClickListner : ClckListner? = null

    fun changeData(newData:ArrayList<String>){

        val oldData = colors
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback(
                oldData,
                newData
            )
        )
        colors = newData
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ViewHolder (
       val binding: FragmentSelectColorListDialogItemBinding
    ) : RecyclerView.ViewHolder(
        binding.root) {
        fun bind(color: String) = with(itemView) {
            binding.color.setBackgroundColor(Color.parseColor(color))


        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentSelectColorListDialogItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(colors.get(position))

    override fun getItemCount(): Int {
        return colors.size
    }



    class ItemsDiffCallback(
        private val oldData: ArrayList<String>,
        private val newData: ArrayList<String>
    ): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

    }

    interface ClckListner{
        fun clickListner(itemColor : String , isCheck : Boolean)
    }

}
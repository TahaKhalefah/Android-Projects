package com.example.souqadmin.ui.product.buttomDialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.souqadmin.databinding.FragmentSelectSizeListDialogItemBinding

class SizeAdapter internal constructor() : RecyclerView.Adapter<SizeAdapter.ViewHolder>() {
    private var sizeList = arrayOf<String>()
    private var selectedSize:ArrayList<String>?=null
    private var selectedSizeWithEdit:Array<String>?=null
    var setOnItemClickListner : ClckListner? = null
    private var chPosition=-1

    fun changeData(newData:Array<String>, selectedSizeWithEdit:Array<String>?=null , selectedSize : ArrayList<String>? = null){
        this.selectedSize =selectedSize
        this.selectedSizeWithEdit =selectedSizeWithEdit
        val oldData = sizeList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback(
                oldData,
                newData
            )
        )
        sizeList = newData
        diffResult.dispatchUpdatesTo(this)
    }



    inner class ViewHolder (
        val binding: FragmentSelectSizeListDialogItemBinding
    ) : RecyclerView.ViewHolder(
        binding.root) {
        fun bind(size: String, position: Int) = with(itemView) {
            binding.size.text = size

            if(selectedSize != null) {
                for (i in selectedSize!!) {
                    if (i==size){
                       binding.checkBox.isChecked=true
                    }
                }
            }

            if(selectedSizeWithEdit != null) {
                for (i in selectedSizeWithEdit!!) {
                    if (i==size){
                        binding.checkBox.isChecked=true
                    }
                }
            }

            if (setOnItemClickListner != null) {
                binding.checkBox.setOnClickListener {
                    if (binding.checkBox.isChecked()) {
                        setOnItemClickListner!!.clickListner(size, true )
                    } else {
                        setOnItemClickListner!!.clickListner(size, false)
                    }
                }
            }

        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentSelectSizeListDialogItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(sizeList.get(position),position)

    override fun getItemCount(): Int {
        return sizeList.size
    }



    class ItemsDiffCallback(
        private val oldData: Array<String>,
        private val newData: Array<String>
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
        fun clickListner(itemColor : String , isCheck : Boolean )
    }

}
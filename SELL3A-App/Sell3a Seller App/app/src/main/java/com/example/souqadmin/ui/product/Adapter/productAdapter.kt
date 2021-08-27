package com.example.souqadmin.ui.product.Adapter


import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqadmin.databinding.ProductItemRvBinding
import com.example.souqadmin.pojo.DeleteProductRequest
import com.example.souqadmin.pojo.ProductResponse

class ProductAdapter(val context: Context) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var items = arrayListOf<ProductResponse.Item>()
    var setOnDeleteClickListener: OnclickListener? = null
    var setOnEditClickListener: OnclickListener? = null
    private lateinit var _viewBinding: ProductItemRvBinding
    val viewBinding: ProductItemRvBinding get() = _viewBinding

    fun changeData(newData: ArrayList<ProductResponse.Item>, clearOldData: Boolean = false) {
        if (clearOldData) {
            items = newData
            notifyDataSetChanged()
        }
        val oldData = items
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ProductAdapter.ItemsDiffCallback(
                oldData,
                newData
            )

        )
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val binding: ProductItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(item: ProductResponse.Item, position: Int) = with(itemView) {

            Glide.with(context)
                .load(item.image)
                .fitCenter()
                .into(binding.productImg)

            binding.productTitle.text = item.title
            binding.productCount.text = "Count : " + item.quantity.toString()
            binding.productCategory.text = "Category :" + item.category.name
            binding.productPrice.text = "Price : " + item.price.toString()

            if (item.quantity <= 5) {
                binding.lowInStack.visibility = View.VISIBLE
                binding.lowInStack.text = "Law in stack"
                if (item.quantity == 0) {
                    binding.lowInStack.text = "Sold Out"
                }
            }

            if (setOnEditClickListener != null) {
                binding.productEditBtn.setOnClickListener {
                    setOnEditClickListener!!.clickListener(item, it, position)
                }
            }
            if (setOnDeleteClickListener != null) {
                binding.deleteBtn.setOnClickListener {
                    notifyItemRemoved(position)
                    setOnDeleteClickListener!!.clickListener(item, it, position)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _viewBinding =
            ProductItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            _viewBinding
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.binding(items.get(position), position)


    class ItemsDiffCallback(
        private val oldData: ArrayList<ProductResponse.Item>,
        private val newData: ArrayList<ProductResponse.Item>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].title == newData[newItemPosition].title
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

    interface OnclickListener {
        fun clickListener(Product: ProductResponse.Item, view: View, position: Int)
    }

}
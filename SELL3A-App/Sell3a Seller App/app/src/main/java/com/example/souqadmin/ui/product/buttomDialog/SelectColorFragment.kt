package com.example.souqadmin.ui.product.buttomDialog

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.souqadmin.databinding.FragmentSelectColorListDialogBinding
import com.example.souqadmin.ui.product.buttomDialog.adapter.ColorAdapter
import com.example.souqadmin.utils.ProuductData
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.OnColorSelectedListener
import com.flask.colorpicker.builder.ColorPickerClickListener
import com.flask.colorpicker.builder.ColorPickerDialogBuilder


class SelectColorFragment() : BottomSheetDialogFragment() {
    private var _binding: FragmentSelectColorListDialogBinding? = null
    private val binding get() = _binding!!
    private var colorList = arrayListOf<String>()
    private lateinit var colorAdapter: ColorAdapter
    private val args by navArgs<SelectColorFragmentArgs>()
    private var colors : Array<String>? = null
    private var currentBackgroundColor : Int = 0xfffffff
    companion object{
    var setOnColorDelete:OncolorDeleted?=null
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectColorListDialogBinding.inflate(inflater, container, false)



        colorAdapter = ColorAdapter()
        var colorList = arrayListOf<String>()
        binding.list.adapter = colorAdapter
            colors  = args.colorList
            colorList = colors!!.toCollection(ArrayList())
        colorAdapter.changeData(colorList)


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                colorList.removeAt(viewHolder.adapterPosition)
                ProuductData.colorItems = colorList
                // below line is to notify our item is removed from adapter.
                colorAdapter.notifyItemRemoved(viewHolder.adapterPosition)

            }
        }).attachToRecyclerView(binding.list)

        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        setOnColorDelete!!.onDelete(ProuductData.colorItems)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // colorAdapter clicklistner
        colorAdapter.setOnItemClickListner = object: ColorAdapter.ClckListner{
            override fun clickListner(itemColor: String, isCheck: Boolean ) {
                if(isCheck)
                    ProuductData.colorItems!!.add(itemColor)
                else
                    ProuductData.colorItems!!.remove(itemColor)
            }
        }
        binding.selectColor.setOnClickListener{
            colorDialog()
        }


        if(ProuductData.colorItems.size>0)
            Log.e("ssss", ProuductData.colorItems!!.size.toString())


    }



    private fun colorDialog(){
        ColorPickerDialogBuilder
            .with(context)
            .setTitle("Choose color")
            .initialColor(currentBackgroundColor)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setOnColorSelectedListener(object : OnColorSelectedListener {
                override fun onColorSelected(selectedColor: Int) {
                    toast("onColorSelected: 0x" + Integer.toHexString(selectedColor))
                }
            })
            .setPositiveButton("ok", object : ColorPickerClickListener {
                override fun onClick(
                    dialog: DialogInterface?,
                    selectedColor: Int,
                    allColors: Array<Int?>?
                ) {
                    ProuductData.colorItems.add("#"+Integer.toHexString(selectedColor))
                    Log.e("ssss","selected color : "+ ProuductData.colorItems.toString(), )
                    changeBackgroundColor(selectedColor)
                }
            })
            .setNegativeButton("cancel",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.dismiss()
                }
            })
            .showColorEdit(true)
            .setColorEditTextColor(ContextCompat.getColor(requireContext(), android.R.color.holo_blue_bright))
            .build()
            .show();
    }

    private fun changeBackgroundColor(selectedColor: Int) {
        currentBackgroundColor = selectedColor
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    interface OncolorDeleted{
        fun onDelete(colorList: ArrayList<String>)
    }

}
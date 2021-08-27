package com.example.souqadmin.ui.product.buttomDialog

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.souqadmin.R
import com.example.souqadmin.databinding.FragmentSelectSizeListDialogBinding
import com.example.souqadmin.ui.product.buttomDialog.adapter.SizeAdapter
import com.example.souqadmin.utils.ProuductData


class SelectSizeFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSelectSizeListDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var sizeAdapter: SizeAdapter
    private val args by navArgs<SelectSizeFragmentArgs>()
    private var sizeList : Array<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectSizeListDialogBinding.inflate(inflater, container, false)

        var category = args.category
        Log.e("ssss", category, )


        val type_shoes = (resources.getStringArray(R.array.shoes_size_list))
        val type_clothes = (resources.getStringArray(R.array.clothes_size_list))
        sizeList = args.sizeList
        sizeAdapter = SizeAdapter()
        binding.list.adapter = sizeAdapter

        if(category == "man shoes"){
            if (args.sizeList!=null){
                sizeAdapter.changeData(type_shoes,args.sizeList)
            }
            if(ProuductData.sizeItems.size != 0){
                sizeAdapter.changeData(type_shoes,args.sizeList , ProuductData.sizeItems)
            }else
            sizeAdapter.changeData(type_shoes)

        }else if(category == "women's clothing" || category == "men's clothing" || category == "women dress"
                || category == "women pants" ){

            if (args.sizeList!=null){
                sizeAdapter.changeData(type_clothes,args.sizeList)
            }
            if(ProuductData.sizeItems.size != 0){
                sizeAdapter.changeData(type_clothes,args.sizeList , ProuductData.sizeItems)
            }else
            sizeAdapter.changeData(type_clothes)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // sizeAdapter clicklistner
        sizeAdapter.setOnItemClickListner = object: SizeAdapter.ClckListner{
            override fun clickListner(itemSize: String, isCheck: Boolean ) {
                if(isCheck)
                    ProuductData.sizeItems!!.add(itemSize)
                else
                    ProuductData.sizeItems!!.remove(itemSize)
            }
        }
    }


}
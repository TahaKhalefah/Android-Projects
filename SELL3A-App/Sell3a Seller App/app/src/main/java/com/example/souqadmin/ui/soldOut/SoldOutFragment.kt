package com.example.souqadmin.ui.soldOut

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.souqadmin.databinding.FragmentSoldOutBinding
import com.example.souqadmin.pojo.ProductResponse
import com.example.souqadmin.ui.low_in_stack.LowInStackFragmentDirections
import com.example.souqadmin.ui.soldOut.adapter.SoldOutAdapter
import com.example.souqadmin.utils.Constants
import com.example.souqadmin.utils.Resource


class SoldOutFragment : Fragment() {

    private lateinit var adapter: SoldOutAdapter
    private var _binding: FragmentSoldOutBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : SoldOutViewModel
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSoldOutBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(SoldOutViewModel ::class.java)
        viewModel.getItemsByCount(getCompanyName() , "0")
        adapter = SoldOutAdapter(requireContext())
        binding.productList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(
            Constants.USER_SHARED_PREF,
            Context.MODE_PRIVATE
        )

        subscribeToLiveData()
        //recycler actions
        adapter.setOnEditClickListener = object : SoldOutAdapter.OnclickListener{
            override fun clickListener(product : ProductResponse.Item, view : View, position : Int) {
                val action = SoldOutFragmentDirections.actionSoldOutFragmentToAddProductFragment(product)
                view?.findNavController()?.navigate(action)
            }
        }


    }


    private fun subscribeToLiveData() {
        viewModel.productsLiveData.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.productProgress.showShimmerAdapter()
                    binding.productList.visibility = View.INVISIBLE
                    Log.e("sss", "loading........")
                }
                Resource.Status.ERROR -> {
                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        binding.productProgress.hideShimmerAdapter()
                        binding.productList.visibility = View.VISIBLE
                        if(! it!!.isEmpty() ) {
                            adapter.changeData(it!!, true)
                        }else{
                            binding.productList.visibility = View.GONE
                            binding.text.visibility = View.VISIBLE
                        }
                    }

                }
            }
        })
    }

    fun getCompanyName(): String {
        val sharedPreferences = requireContext().getSharedPreferences(
            Constants.USER_SHARED_PREF,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString("name", "")!!
    }

}
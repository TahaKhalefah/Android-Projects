package com.example.souqadmin.ui.orders

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.souqadmin.databinding.FragmentOrdersBinding
import com.example.souqadmin.pojo.OrderResponseItem
import com.example.souqadmin.ui.orders.adapter.OrderAdapter
import com.example.souqadmin.utils.Constants
import com.example.souqadmin.utils.Resource

class OrdersFragment : Fragment() {

    lateinit var binding: FragmentOrdersBinding
    lateinit var adapter: OrderAdapter
    lateinit var viewModel: OrderViewModel
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        binding.swiperefreshlayout.setOnClickListener {
            getAllOrders()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initOrderRecyclerView()
        sharedPreferences = requireContext().getSharedPreferences(
            Constants.USER_SHARED_PREF,
            Context.MODE_PRIVATE
        )
        viewModel.getOrderByCompanyName(getCompanyName())
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

    }

    private fun initOrderRecyclerView() {
        adapter = OrderAdapter { view, orderResponseItem, i ->
            val action = OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(
                orderResponseItem
            )
            view.findNavController().navigate(action)

        }
        getAllOrders()
        binding.ordersRv.adapter = adapter

    }

    private fun getAllOrders() {
        viewModel.orders.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("TAG", "getAllOrders: LOADING")
                    binding.orderProgress.showShimmerAdapter()
                }
                Resource.Status.ERROR -> {
                    Log.e("TAG", "getAllOrders: ERROR" + it.message)
                    binding.orderProgress.showShimmerAdapter()
                }
                Resource.Status.SUCCESS -> {
                    binding.orderProgress.hideShimmerAdapter()
                    it.data.let {
                        Log.i("s", it?.size.toString())
                        adapter.changeData((it!!))
                        binding.ordersRv.adapter = adapter
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
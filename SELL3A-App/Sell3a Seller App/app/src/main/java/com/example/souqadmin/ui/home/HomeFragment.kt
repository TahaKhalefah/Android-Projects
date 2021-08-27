package com.example.souqadmin.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.souqadmin.R
import com.example.souqadmin.databinding.FragmentHomeBinding
import com.example.souqadmin.databinding.FragmentProductBinding
import com.example.souqadmin.utils.Constants
import com.example.souqadmin.utils.Resource

class HomeFragment : Fragment()  {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var productCount = 0
    var orderCount = 0
    var soldOutProductCount = 0
    var lowInStackProductCount = 0

    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.getItemsByCompanyName(getCompanyName())
        homeViewModel.getOrderByCompanyName(getCompanyName())
        homeViewModel.getSoldOutItemsByCount(getCompanyName() , "5")
        homeViewModel.getSoldOutItemsByCount(getCompanyName() , "0")

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productCard.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_nav_home_to_productFragment)
        }

        binding.orderCard.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_nav_home_to_ordersFragment)
        }


        binding.lowInStackCard.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_nav_home_to_lowInStackFragment)
        }

        binding.soldOutCard.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_nav_home_to_soldOutFragment)
        }

        subscribeToLiveData()

        binding.productsCount.text = productCount.toString()
        binding.soldoutCount.text = soldOutProductCount.toString()
        binding.lowInStackCount.text = lowInStackProductCount.toString()
        binding.ordersCount.text = orderCount.toString()


    }


    private fun subscribeToLiveData() {
        homeViewModel.productsLiveData.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.ERROR -> {
                    Log.i("TAG", "error")
                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        productCount = it!!.size
                        binding.productsCount.text = productCount.toString()
                        Log.e("TAG", productCount.toString(), )
                    }
                }
            }
        })

        homeViewModel.soldOutLiveData.observe(requireActivity(), Observer {
            when (it.status) {

                Resource.Status.SUCCESS -> {
                    it.data.let {
                        soldOutProductCount = it!!.size
                        binding.soldoutCount.text = soldOutProductCount.toString()
                        Log.e("TAG sold", soldOutProductCount.toString(), )

                    }

                }
            }
        })

        homeViewModel.lowInStackLiveData.observe(requireActivity(), Observer {
            when (it.status) {

                Resource.Status.SUCCESS -> {
                    it.data.let {
                        lowInStackProductCount = it!!.size
                        binding.lowInStackCount.text = lowInStackProductCount.toString()
                        Log.e("TAG low", lowInStackProductCount.toString(), )

                    }

                }
            }
        })

        homeViewModel.orders.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    orderCount = it!!.data!!.size
                    binding.ordersCount.text = orderCount.toString()

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
package com.example.souqadmin.ui.product

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
import com.example.souqadmin.databinding.FragmentProductBinding
import com.example.souqadmin.pojo.DeleteProductRequest
import com.example.souqadmin.pojo.ProductResponse
import com.example.souqadmin.ui.product.Adapter.ProductAdapter
import com.example.souqadmin.utils.Constants
import com.example.souqadmin.utils.Resource


class ProductFragment : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    private var _binding: FragmentProductBinding? = null
    private lateinit var viewModel: ProductViewModel
    lateinit var sharedPreferences: SharedPreferences

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        subscribeToLiveData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = context.getSharedPreferences(
            Constants.USER_SHARED_PREF,
            Context.MODE_PRIVATE
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModel.getItemsByCompanyName(getCompanyName())
        initCartRecyclerView()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(
            Constants.USER_SHARED_PREF,
            Context.MODE_PRIVATE
        )

        Log.i("TAG", getCompanyName())

        //recycler actions
        productAdapter.setOnEditClickListener = object : ProductAdapter.OnclickListener {
            override fun clickListener(product: ProductResponse.Item, view: View, position: Int) {
                val action =
                    ProductFragmentDirections.actionProductFragmentToAddProductFragment(product)
                view?.findNavController()?.navigate(action)
            }
        }


        productAdapter.setOnDeleteClickListener = object : ProductAdapter.OnclickListener {
            override fun clickListener(Product: ProductResponse.Item, view: View, position: Int) {

                var deleteProduct = DeleteProductRequest(Product.id.toString())
                viewModel.deleteProduct(deleteProduct)
                subscribeToLiveData()

            }
        }

        // add product action
        binding.addProduct.setOnClickListener {
            val action = ProductFragmentDirections.actionProductFragmentToAddProductFragment(null)
            view?.findNavController()?.navigate(action)
        }
    }


    private fun initCartRecyclerView() {
        productAdapter = ProductAdapter(requireContext())
        binding.productList.apply {
            adapter = productAdapter
        }
    }

    private fun subscribeToLiveData() {
        viewModel.productsLiveData.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sss", "loading........")
                    binding.productProgress.showShimmerAdapter()
                    binding.productList.visibility = View.INVISIBLE
                    Log.i("TAG", "loading")

                }
                Resource.Status.ERROR -> {
                    Log.i("TAG", "error")
                    binding.productProgress.hideShimmerAdapter()
                    binding.productList.visibility = View.GONE
                    binding.text.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        productAdapter.changeData(it!!, true)
                    }
                    binding.productProgress.hideShimmerAdapter()
                    binding.productList.visibility = View.VISIBLE
                    Log.i("TAG", it.data!!.size.toString())

                }
            }
        })

        viewModel.deleteProductLiveData.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sss", "loading........")
                }
                Resource.Status.ERROR -> {
                    Log.e("ssss", it.message.toString())
                }
                Resource.Status.SUCCESS -> {
                    Log.e("ssss", it.data!!.toString())
                    viewModel.getItemsByCompanyName(companyName = getCompanyName())

                }
            }
        })

    }

    fun getCompanyName(): String {

        return sharedPreferences.getString("name", "")!!
    }

}
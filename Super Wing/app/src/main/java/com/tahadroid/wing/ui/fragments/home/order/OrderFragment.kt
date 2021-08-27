package com.tahadroid.wing.ui.fragments.home.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.tahadroid.wing.R
import com.tahadroid.wing.models.Menu
import com.tahadroid.wing.ui.fragments.home.order.delivery.DeliveryFragment
import com.tahadroid.wing.ui.fragments.home.order.review.ReviewFragment
import kotlinx.android.synthetic.main.fragment_order.*


class OrderFragment : Fragment(R.layout.fragment_order) {
    private lateinit var ordersViewModel: OrderViewModel
    private lateinit var orderAdapter: OrderAdapter
    val args: OrderFragmentArgs by navArgs()
    var list: ArrayList<Menu>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToParent(view)
        Glide.with(requireContext()).load(args.imagePath).into(imageViewMeal)
        initViewModel()
        ordersViewModel.getMenus(args.pid, args.shopName)
        setupObserver()

        view_pager2.adapter = ViewStateAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(tabLayout, view_pager2) { tab, position ->
            if (position == 0) {
                tab.text = "Delivery"
            } else if (position == 1) {
                tab.text = "Review"
            }
            view_pager2.setCurrentItem(tab.position, true)
        }.attach()
    }

    private fun initViewModel() {
        ordersViewModel =
            ViewModelProvider(requireActivity()).get(OrderViewModel::class.java)
    }

    private fun setupObserver() {
        ordersViewModel.menus
            .observe(viewLifecycleOwner, Observer { list ->
                setupRecyclerView(list)
            })
    }

    private fun setupRecyclerView(list: List<Menu>) {
        orderAdapter = OrderAdapter { view, menu, i ->
            val action = OrderFragmentDirections
                .actionOrderFragmentToBottomSheetOrder(
                    menu.shopName.toString(),
                    menu.name.toString(),
                    menu.price,
                    args.pid,
                    menu.image.toString(),
                    menu
                )
            view.findNavController().navigate(action)
        }
        orderAdapter.swapData(list)
        orderAdapter.notifyDataSetChanged()
        menuRecyclerView.apply {
            adapter = orderAdapter
        }
    }


    private fun backToParent(view: View) {
        imageViewBack.setOnClickListener {
            val action = OrderFragmentDirections.actionOrderFragmentToFoodsFragment()
            view.findNavController().navigate(action)
        }
    }

    private class ViewStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun createFragment(position: Int): Fragment {
            // Hardcoded in this order, you'll want to use lists and make sure the titles match
            return if (position == 0) {
                DeliveryFragment()
            } else ReviewFragment()
        }

        override fun getItemCount(): Int {
            // Hardcoded, use lists
            return 2
        }
    }

}
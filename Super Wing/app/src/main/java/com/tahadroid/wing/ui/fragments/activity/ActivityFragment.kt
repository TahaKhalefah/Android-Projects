package com.tahadroid.wing.ui.fragments.activity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tahadroid.wing.R
import com.tahadroid.wing.models.ActivityCategory
import com.tahadroid.wing.models.Cart
import kotlinx.android.synthetic.main.fragment_activity.*


class ActivityFragment : Fragment(R.layout.fragment_activity) {

    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var activityCategotyAdapter: ActivityCategotyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initViewModel()
//        setObserver()
        setStatusColor()
        setupRecyclerView()
    }

    private fun setObserver() {
        activityViewModel.getRecent().observe(viewLifecycleOwner, Observer { cart ->
            setData(cart)
            Log.d(TAG, "setObserver: " + cart.Total)
        })
    }

    private fun setData(cart: Cart) {
        Glide.with(requireContext()).load(cart.orders.get(0).image).into(imageViewPlace)
        textViewTitle.text = cart.orders.get(0).placeName
        textViewDescription.text = cart.orders[0].quantity.toString() + "-" + cart.orders[0].title
        textViewDate.text = cart.date
    }

    private fun initViewModel() {
        activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)
    }

    private fun setStatusColor() {
        val window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.white)
    }

    private fun setupRecyclerView() {
        activityCategotyAdapter = ActivityCategotyAdapter { view, activityCategory, i ->
        }
        val arr = ArrayList<ActivityCategory>()
        arr.add(ActivityCategory("Activity"))
        arr.add(ActivityCategory("Foods"))
        arr.add(ActivityCategory("Shops"))
        activityCategotyAdapter.swapData(arr)
        activityRecyclerView.apply {
            adapter = activityCategotyAdapter
        }
    }

}
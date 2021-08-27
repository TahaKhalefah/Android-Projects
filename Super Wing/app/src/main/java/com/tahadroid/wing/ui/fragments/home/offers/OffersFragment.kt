package com.tahadroid.wing.ui.fragments.home.offers

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.tahadroid.wing.R
import com.tahadroid.wing.models.Menu
import com.tahadroid.wing.ui.fragments.home.offers.adapters.OffersAdapter
import com.tahadroid.wing.ui.fragments.home.offers.viewmodel.OfferViewModel
import kotlinx.android.synthetic.main.fragment_offers.*


class OffersFragment : Fragment(R.layout.fragment_offers), View.OnClickListener {

    private lateinit var offerViewModel: OfferViewModel
    private lateinit var offerAdapter: OffersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageViewFinish.setOnClickListener(this)
        buttonRefresh.setOnClickListener(this)
        initViewModel()
        checkNetwork()
    }

    fun isNetworkAvailable(con: Context): Boolean {
        try {
            val cm = con
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun checkNetwork() {
        if (isNetworkAvailable(requireContext())) {
            setupShopsObserver()
            linearLayoutConnected.visibility = View.VISIBLE
            linearLayoutDisconnected.visibility = View.GONE
        } else {
            linearLayoutConnected.visibility = View.GONE
            linearLayoutDisconnected.visibility = View.VISIBLE
        }

    }

    private fun setupShopsObserver() {
        offerViewModel.getOffers().observe(requireActivity(), Observer {
            setupOfferRecyclerView(it)
        })
    }


    private fun setupOfferRecyclerView(it: List<Menu>) {
        offerAdapter = OffersAdapter { view, shop, i ->
        }
        offerAdapter.swapData(it)
        offerRecyvlerView.apply {
            adapter = offerAdapter
        }
    }

    private fun initViewModel() {
        offerViewModel = ViewModelProvider(requireActivity()).get(OfferViewModel::class.java)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageViewFinish -> findNavController().popBackStack()
            R.id.buttonRefresh -> {
                val action = OffersFragmentDirections.actionOffersFragmentSelf()
                view?.findNavController()?.navigate(action)
            }
        }
    }

}
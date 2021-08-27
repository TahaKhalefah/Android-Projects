package com.tahadroid.wing.ui.fragments.home.homemade

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
import com.tahadroid.wing.models.Section
import kotlinx.android.synthetic.main.fragment_home_made.*


class HomeMadeFragment : Fragment(R.layout.fragment_home_made),View.OnClickListener {
    private lateinit var homeMadeViewModel: HomeMadeViewModel
    private lateinit var homeMadeAdapter: HomeMadeAdapter
    private lateinit var sectionAdapter: SectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        checkNetwork()
        buttonRefresh.setOnClickListener(this)
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
            homeMadeProgressbar.visibility = View.VISIBLE
            getSectionData()
            constraintLayoutCinnected.visibility = View.VISIBLE
            homeMadeProgressbar.visibility = View.GONE
        } else {
            constraintLayoutCinnected.visibility = View.GONE
            constraintLayoutDisconnected.visibility = View.VISIBLE
        }

    }

    private fun initViewModel() {
        homeMadeViewModel = ViewModelProvider(requireActivity()).get(HomeMadeViewModel::class.java)
    }

    private fun getSectionData() {
        homeMadeViewModel.getSections().observe(viewLifecycleOwner, Observer {
            setupSectionRecyclerView(it)
        })
    }

    private fun setupSectionRecyclerView(it: List<Section>) {
        sectionAdapter = SectionAdapter { view, section, i ->
            homeMadeProgressbar.visibility = View.VISIBLE
            homeMadeViewModel.getDishs(section.sectionName.toString())
            homeMadeViewModel.dishs.observe(viewLifecycleOwner,
                Observer {
                    setupDishRecyclerView(it)
                })
            homeMadeProgressbar.visibility = View.GONE

        }
        sectionAdapter.swapData(it)
        sectionAdapter.notifyDataSetChanged()
        sectionRecyclerView.apply {
            adapter = sectionAdapter

        }
    }

    private fun setupDishRecyclerView(it: List<Menu>) {
        homeMadeAdapter = HomeMadeAdapter { view, menu, i ->
        }
        homeMadeAdapter.swapData(it)
        homeMadeAdapter.notifyDataSetChanged()
        typeRecyclerView.apply {
            adapter = homeMadeAdapter
        }
    }

    override fun onClick(v: View) {
      when(v.id){
          R.id.buttonRefresh ->{
              val action = HomeMadeFragmentDirections.actionHomeMadeFragmentSelf()
              view?.findNavController()?.navigate(action)
          }
      }
    }
}
package com.tahadroid.wing.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.database.*
import com.tahadroid.wing.R
import com.tahadroid.wing.models.Shop
import com.tahadroid.wing.ui.map.MapActivity
import kotlinx.android.synthetic.main.fragment_home.*


open class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var homeViewModel: HomeViewModel
    private val rootRef: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }
    private lateinit var resRef: DatabaseReference
    private lateinit var shpRef: DatabaseReference

    var listRestaurantCat: ArrayList<Shop>? = null
    var listShopCat: ArrayList<Shop>? = null

    var listAll: ArrayList<Shop>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusColor()
        nestedScroll.isNestedScrollingEnabled = true

        addCardTextView.setOnClickListener(this)
        cardViewDish.setOnClickListener(this)
        cardViewFoods.setOnClickListener(this)
        cardViewShops.setOnClickListener(this)
        cardViewDelivery.setOnClickListener(this)
        cardViewOffers.setOnClickListener(this)


        editText_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    listRestaurantCat?.clear()
                    search(query.toLowerCase())
                } else {
                    search("")
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setObserver() {

        listAll = ArrayList()
        Log.d("TAG", "setObserver res listAll : " + listAll?.size)
        homeViewModel.getRestaurantsData().observe(requireActivity(), Observer {
            listAll!!.addAll(it)
            Log.d("TAG", "setObserver res listAll : " + listAll?.size)
            Log.d("TAG", "setObserver res it : " + it?.size)
        })
        homeViewModel.getShopsData().observe(requireActivity(), Observer {
            listAll!!.addAll(it)
            Log.d("TAG", "setObserver shp listAll : " + listAll?.size)
            Log.d("TAG", "setObserver shp it : " + it?.size)
        })

    }


    private fun setStatusColor() {
        val window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.colorPrimary)
    }


    private fun search(str: String) {
        listRestaurantCat = ArrayList()
        listShopCat = ArrayList()

        resRef = rootRef.child("Restaurants")
        val queryRes = resRef.orderByChild("shopName")
            .startAt(str)
            .endAt(str + "\uf8ff")
        queryRes.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChildren()) {
                    listRestaurantCat?.clear()
                    for (n in snapshot.children) {
                        val res = n.getValue(Shop::class.java)
                        listRestaurantCat?.add(res!!)
                    }
                    setupRecyclerView(listRestaurantCat!!)
//                    listAll?.addAll(listRestaurantCat!!)
                }
            }
        })
//        shpRef = rootRef.child("Shops")
//        val queryshp = shpRef.orderByChild("shopName")
//            .startAt(str)
//            .endAt(str + "\uf8ff")
//        queryshp.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {}
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.hasChildren()) {
//                    listShopCat?.clear()
//                    for (n in snapshot.children) {
//                        val res = n.getValue(Shop::class.java)
//                        listShopCat?.add(res!!)
//                    }
//                    listAll?.addAll(listShopCat!!)
//                }
//            }
//        }) setupRecyclerView(listAll!!)
    }


    private fun initViewModel() {
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    private fun setupRecyclerView(listAll: ArrayList<Shop>) {
        Log.d("TAG", "setupRecyclerView: " + listAll.size)
        homeAdapter = HomeAdapter { view, shop, i ->
        }
        if (listAll.size > 0) {
            Log.d("TAG", "setupRecyclerView if : " + listAll.size)
            advertisementRecyclerView.visibility = View.VISIBLE
            advertisementView.visibility = View.GONE
            textViewadv.visibility = View.GONE
            textViewadver.visibility = View.GONE
            imageViewadv.visibility = View.GONE
            homeAdapter.swapData(listAll)
            homeAdapter.notifyDataSetChanged()
        } else {
            Log.d("TAG", "setupRecyclerView else : " + listAll.size)
            advertisementRecyclerView.visibility = View.GONE
            advertisementView.visibility = View.VISIBLE
            textViewadv.visibility = View.VISIBLE
            textViewadver.visibility = View.VISIBLE
            imageViewadv.visibility = View.VISIBLE
        }
        advertisementRecyclerView?.apply {
            adapter = homeAdapter
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.addCardTextView -> {
                val action = HomeFragmentDirections.actionHomeFragmentToAddCardFragment()
                view?.findNavController()?.navigate(action)
            }
            R.id.cardViewDish -> {
                val action = HomeFragmentDirections.actionHomeFragmentToHomeMadeFragment()
                view?.findNavController()?.navigate(action)
            }
            R.id.cardViewFoods -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFoodsFragment()
                view?.findNavController()?.navigate(action)
            }
            R.id.cardViewShops -> {
                val action = HomeFragmentDirections.actionHomeFragmentToShopsFragment()
                view?.findNavController()?.navigate(action)
            }
            R.id.cardViewDelivery -> {
                startActivity(Intent(activity, MapActivity::class.java))
            }
            R.id.cardViewOffers -> {
                val action = HomeFragmentDirections.actionHomeFragmentToOffersFragment()
                view?.findNavController()?.navigate(action)
            }
        }
    }
}
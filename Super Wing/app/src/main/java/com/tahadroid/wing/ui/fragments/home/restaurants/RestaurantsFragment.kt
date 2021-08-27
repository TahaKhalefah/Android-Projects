package com.tahadroid.wing.ui.fragments.home.restaurants

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.tahadroid.wing.R
import com.tahadroid.wing.glideApp.GlideApp
import com.tahadroid.wing.models.Category
import com.tahadroid.wing.models.Shop
import com.tahadroid.wing.models.User
import com.tahadroid.wing.ui.fragments.home.restaurants.adapters.ExploreRestaurantAdapter
import com.tahadroid.wing.ui.fragments.home.restaurants.adapters.PopularRestaurantAdapter
import com.tahadroid.wing.ui.fragments.home.restaurants.adapters.RecommendedRestaurantAdapter
import com.tahadroid.wing.ui.fragments.home.restaurants.viewmodel.RestaurantsViewModel
import com.tahadroid.wing.ui.fragments.home.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_foods.*
import kotlinx.android.synthetic.main.fragment_foods.buttonRefresh
import kotlinx.android.synthetic.main.fragment_foods.circleImageView
import kotlinx.android.synthetic.main.fragment_foods.constraintLayoutConnected
import kotlinx.android.synthetic.main.fragment_foods.constraintLayoutDisconnected
import kotlinx.android.synthetic.main.fragment_foods.exploreShopRecyclerView
import kotlinx.android.synthetic.main.fragment_foods.recommendedShopRecyclerView


class RestaurantsFragment : Fragment(R.layout.fragment_foods), View.OnClickListener {
    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val databaseInstance: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }
    private val storgeInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }
    var listCategory: ArrayList<Category>? = null
    var listCat: ArrayList<Category>? = null
    lateinit var listPop: ArrayList<Shop>

    private lateinit var popularRestaurantAdapter: PopularRestaurantAdapter
    private lateinit var exploreRestaurantAdapter: ExploreRestaurantAdapter
    private lateinit var recommendedRestaurantAdapter: RecommendedRestaurantAdapter

    private lateinit var restaurantsViewModel: RestaurantsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listPop= ArrayList()
        initViewModel()
        getUserData()
        checkNetwork()
        buttonRefresh.setOnClickListener(this)
        editTextRestaurantTextSearch.setOnClickListener(this)
    }
    private fun getUserData() {
        val ref =databaseInstance.child("Users").child(mAuth.currentUser?.uid.toString())
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user?.profileImage?.isNotEmpty()!!) {
                    GlideApp.with(requireContext())
                        .load(storgeInstance.getReference(user.profileImage!!))
                        .into(circleImageView)
                } else {
                    circleImageView.setImageResource(R.drawable.ic_userprofileimage)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun initViewModel() {
        restaurantsViewModel =
            ViewModelProvider(requireActivity()).get(RestaurantsViewModel::class.java)
    }

    private fun setupObserver() {
        restaurantsViewModel.getRestaurantsData().observe(requireActivity(), Observer { list ->
            listPop = list as ArrayList<Shop>
            setupPopularRecyclerView(list)
            setupExploreRecyclerView(list)
            setupRecomendedRecyclerView(list)
        })
    }

    private fun setupPopularRecyclerView(list: List<Shop>) {
        popularRestaurantAdapter =
            PopularRestaurantAdapter { view, restaurant, i ->
                val action = RestaurantsFragmentDirections.actionFoodsFragmentToOrderFragment(
                    restaurant.shopId.toString(),
                    restaurant.shopName.toString(),
                    restaurant.image.toString()

                )
                view.findNavController().navigate(action)
            }
        popularRestaurantAdapter.swapData(list)
        popularRecyclerView.apply {
            adapter = popularRestaurantAdapter
        }
    }

    // set data to recycler
    private fun setupExploreRecyclerView(list: List<Shop>) {
        exploreRestaurantAdapter =
            ExploreRestaurantAdapter { view, Food, i ->
            }
        filterData(list)
        exploreRestaurantAdapter.swapData(listCat!!)
        exploreShopRecyclerView.apply {
            adapter = exploreRestaurantAdapter
        }
    }

    private fun filterData(list: List<Shop>) {
        listCategory = ArrayList()
        listCat = ArrayList()
        list.sortedBy {
            it.type
        }
        for (i in list.indices) {
            var count = 1
            for (j in 1 until list.size) {
                if (list[i].type == list[j].type && i != j) {
                    count++
                }
            }
            listCategory!!.add(Category(list[i].type!!, list[i].icon!!, count))
        }
        listCat!!.add(listCategory!![0])
        for (n in 0..listCategory!!.size - 2) {
            if (!(listCategory!![n].name.equals(listCategory!![n + 1].name))) {
                listCat!!.add(listCategory!![n + 1])
            } else {
                continue
            }
        }
    }

    // set data to recycler
    private fun setupRecomendedRecyclerView(list: List<Shop>) {
        recommendedRestaurantAdapter =
            RecommendedRestaurantAdapter { view, type, i ->
            }
        recommendedRestaurantAdapter.swapData(list)
        recommendedShopRecyclerView.apply {
            adapter = recommendedRestaurantAdapter
        }
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
            setupObserver()
            constraintLayoutConnected.visibility = View.VISIBLE
            constraintLayoutDisconnected.visibility = View.GONE
        } else {
            constraintLayoutConnected.visibility = View.GONE
            constraintLayoutDisconnected.visibility = View.VISIBLE
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonRefresh -> {
                val action = RestaurantsFragmentDirections.actionFoodsFragmentSelf()
                view?.findNavController()?.navigate(action)
            }
            R.id.editTextRestaurantTextSearch -> {
                val intent = Intent(requireContext(), SearchActivity::class.java)
                intent.putParcelableArrayListExtra(
                    "myList",
                    listPop as ArrayList<out Parcelable?>?
                )
                startActivity(intent)
            }
        }
    }

}
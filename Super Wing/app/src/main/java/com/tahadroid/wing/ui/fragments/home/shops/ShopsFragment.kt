package com.tahadroid.wing.ui.fragments.home.shops

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
import com.tahadroid.wing.ui.fragments.home.search.SearchActivity
import com.tahadroid.wing.ui.fragments.home.shops.adapters.ExploreShopAdapter
import com.tahadroid.wing.ui.fragments.home.shops.adapters.PopularShopAdapter
import com.tahadroid.wing.ui.fragments.home.shops.adapters.RecommendedShopAdapter
import com.tahadroid.wing.ui.fragments.home.shops.viewmodel.ShopsViewModel
import kotlinx.android.synthetic.main.fragment_shops.*


class ShopsFragment : Fragment(R.layout.fragment_shops), View.OnClickListener {
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

    private lateinit var popularShopAdapter: PopularShopAdapter
    private lateinit var exploreShopAdapter: ExploreShopAdapter
    private lateinit var recommendedShopAdapter: RecommendedShopAdapter

    private lateinit var shopsViewModel: ShopsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listPop= ArrayList()
        getUserData()
        initViewModel()
        checkNetwork()
        buttonRefresh.setOnClickListener(this)
        editTextShopTextSearch.setOnClickListener(this)
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
    private fun setupShopsObserver() {
        shopsViewModel.getShops().observe(requireActivity(), Observer { list ->
            listPop = list as ArrayList<Shop>
            setupShopPopRecyclerView(list)
            setupShopExpRecyclerView(list)
            setupShopRecRecyclerView(list)
        })
    }

    private fun setupShopRecRecyclerView(list: List<Shop>) {
        recommendedShopAdapter = RecommendedShopAdapter { view, shop, i ->
        }
        recommendedShopAdapter.swapData(list)
        recommendedShopRecyclerView.apply {
            adapter = recommendedShopAdapter
        }
    }

    private fun setupShopExpRecyclerView(list: List<Shop>) {

        exploreShopAdapter = ExploreShopAdapter { view, shop, i ->
        }
        filterData(list)
        exploreShopAdapter.swapData(listCat!!)

        exploreShopRecyclerView.apply {
            adapter = exploreShopAdapter
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

    private fun setupShopPopRecyclerView(list: List<Shop>) {
        popularShopAdapter = PopularShopAdapter { view, shop, i ->
            val action = ShopsFragmentDirections.actionShopsFragmentToOrderFragment(
                shop.shopId.toString(),
                shop.shopName.toString(),
                shop.image.toString()
            )
            view.findNavController().navigate(action)
        }
        popularShopAdapter.swapData(list)

        popularShopRecyclerView.apply {
            adapter = popularShopAdapter
        }
    }

    private fun initViewModel() {
        shopsViewModel = ViewModelProvider(requireActivity()).get(ShopsViewModel::class.java)
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
                val action = ShopsFragmentDirections.actionShopsFragmentSelf()
                view?.findNavController()?.navigate(action)
            }
            R.id.editTextShopTextSearch -> {
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
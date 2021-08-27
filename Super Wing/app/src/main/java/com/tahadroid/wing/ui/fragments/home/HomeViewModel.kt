package com.tahadroid.wing.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.tahadroid.wing.models.Shop
import java.util.*

class HomeViewModel : ViewModel() {
    val resLiveData: MutableLiveData<List<Shop>> = MutableLiveData()
    val shpLiveData: MutableLiveData<List<Shop>> = MutableLiveData()

    private lateinit var resRef: DatabaseReference
    private lateinit var shpRef: DatabaseReference

    var listRestaurantCat: ArrayList<Shop>? = null
    var listShopCat: ArrayList<Shop>? = null

    fun getRestaurantsData(): LiveData<List<Shop>> {
        listRestaurantCat = ArrayList()
        resRef = FirebaseDatabase.getInstance().reference.child("Restaurants")
        resRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                listRestaurantCat?.clear()
                for (n in snapshot.children) {
                    val shop = n.getValue(Shop::class.java)
                    listRestaurantCat?.add(shop!!)
                }
                resLiveData.value = listRestaurantCat
            }
        })
        return resLiveData
    }
    fun getShopsData(): LiveData<List<Shop>> {
        listShopCat = ArrayList()
        shpRef = FirebaseDatabase.getInstance().reference.child("Shops")
        shpRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                listShopCat?.clear()
                for (n in snapshot.children) {
                    val shop = n.getValue(Shop::class.java)
                    listShopCat?.add(shop!!)
                }
                shpLiveData.value = listShopCat
            }
        })
        return shpLiveData
    }
}
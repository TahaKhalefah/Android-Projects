package com.tahadroid.wing.ui.fragments.home.restaurants.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tahadroid.wing.models.Shop


class RestaurantsViewModel() : ViewModel() {
    var restaurants: MutableLiveData<List<Shop>> = MutableLiveData()
    var list: ArrayList<Shop>? = null
    fun getRestaurantsData(): LiveData<List<Shop>> {
        list = ArrayList()
        if (restaurants.value == null) {
            FirebaseDatabase.getInstance()
                .reference.child("Restaurants")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {}

                    override fun onDataChange(snapshot: DataSnapshot) {
                        list?.clear()
                        if (snapshot.exists()) {
                            for (snap in snapshot.children) {
                                val restaurant = snap.getValue(Shop::class.java)
                                restaurant?.shopId = snap.key
                                list?.add(restaurant!!)
                            }
                            restaurants.value = list
                        }
                    }


                })
        }
        return restaurants
    }


}


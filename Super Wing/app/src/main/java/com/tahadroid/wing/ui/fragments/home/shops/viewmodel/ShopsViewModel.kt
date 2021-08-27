package com.tahadroid.wing.ui.fragments.home.shops.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tahadroid.wing.models.Shop


class ShopsViewModel() : ViewModel() {
    var shops: MutableLiveData<List<Shop>> = MutableLiveData()
    var list: ArrayList<Shop>? = null


    fun getShops(): LiveData<List<Shop>> {
        list = ArrayList()
        if (shops.value == null) {
            FirebaseDatabase.getInstance()
                .reference.child("Shops")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {}

                    override fun onDataChange(snapshot: DataSnapshot) {
                        list?.clear()
                        if (snapshot.exists()) {
                            for (snap in snapshot.children) {
                                val shop = snap.getValue(Shop::class.java)
                                shop?.shopId = snap.key
                                list?.add(shop!!)
                            }
                            shops.value = list
                        }
                    }


                })
        }
        return shops
    }

}


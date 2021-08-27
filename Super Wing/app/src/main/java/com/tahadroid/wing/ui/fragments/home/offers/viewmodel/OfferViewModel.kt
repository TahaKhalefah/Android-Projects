package com.tahadroid.wing.ui.fragments.home.offers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tahadroid.wing.models.Menu

class OfferViewModel :ViewModel(){
    var shops: MutableLiveData<List<Menu>> = MutableLiveData()
    var list: ArrayList<Menu>? = null


    fun getOffers(): LiveData<List<Menu>> {
        list = ArrayList()
        if (shops.value == null) {
            FirebaseDatabase.getInstance()
                .reference.child("Menu")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {}

                    override fun onDataChange(snapshot: DataSnapshot) {
                        list?.clear()
                        if (snapshot.exists()) {
                            for (snap in snapshot.children) {
                                val menu = snap.getValue(Menu::class.java)
                                if(menu?.discount!! > 0){
                                    list?.add(menu)
                                }
                            }
                            shops.value = list
                        }
                    }


                })
        }
        return shops
    }

}
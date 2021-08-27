package com.tahadroid.wing.ui.fragments.home.order

import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tahadroid.wing.models.Menu


class OrderViewModel : ViewModel() {
    val menus: MutableLiveData<List<Menu>> = MutableLiveData()
    var list: ArrayList<Menu>? = null

    fun getMenus(key: String, shopName: String) {
        list = ArrayList()

        val query = FirebaseDatabase.getInstance()
            .reference.child("Menu").orderByChild("placeKey")
            .startAt(key)
            .endAt(key + "\uf8ff")
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                list?.clear()
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val menu = snap.getValue(Menu::class.java)
                        menu?.shopName = shopName
                        menu?.shopId = snap.key
                        list?.add(menu!!)
                    }
                    menus.value = list
                }
            }
        })

    }
}

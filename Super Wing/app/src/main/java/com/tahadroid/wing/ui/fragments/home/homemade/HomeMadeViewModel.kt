package com.tahadroid.wing.ui.fragments.home.homemade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tahadroid.wing.models.Section
import com.tahadroid.wing.models.Menu

class HomeMadeViewModel : ViewModel() {
    var dishs: MutableLiveData<List<Menu>> = MutableLiveData()
    var sections: MutableLiveData<List<Section>> = MutableLiveData()

    var listDishes: ArrayList<Menu>? = null
    var listSection: ArrayList<Section>? = null

    fun getDishs(dsh: String) {
        listDishes = ArrayList()
        val query = FirebaseDatabase.getInstance().reference.child("Menu").orderByChild("section")
            .startAt(dsh)
            .endAt(dsh + "\uf8ff")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                listDishes?.clear()
                for (snap in snapshot.children) {
                    val menu = snap.getValue(Menu::class.java)
                    listDishes?.add(menu!!)
                }
                dishs.value = listDishes
            }
        })
    }

     fun getSections(): LiveData<List<Section>> {
        listSection = ArrayList()
        FirebaseDatabase.getInstance().reference.child("Section")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    listSection?.clear()
                    for (snap in snapshot.children) {
                        val sec = snap.getValue(Section::class.java)
                        listSection?.add(sec!!)
                    }
                    sections.value = listSection
                }
            })
        return sections
    }
}
package com.tahadroid.wing.ui.fragments.activity

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tahadroid.wing.models.Cart

class ActivityViewModel : ViewModel() {
    val recent: MutableLiveData<Cart> = MutableLiveData()

    private lateinit var ref: DatabaseReference
     fun getRecent():LiveData<Cart> {
        ref = FirebaseDatabase.getInstance().reference.child("Orders")
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString())
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val cart = snapshot.getValue(Cart::class.java)
                recent.value = cart
                Log.d(TAG, "onDataChange: "+cart?.adress +"/"+cart?.orders?.size)
            }
        })
        return recent
    }
}
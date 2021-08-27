package com.tahadroid.wing.ui.fragments.notification

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.tahadroid.wing.R
import com.tahadroid.wing.glideApp.GlideApp
import com.tahadroid.wing.models.User
import kotlinx.android.synthetic.main.fragment_notification.*

class NotificationFragment : Fragment(R.layout.fragment_notification) {
    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val databaseInstance: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }
    private val storgeInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusColor()

        getUserData()
    }

    private fun setStatusColor() {
        val window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.white)
    }

    private fun getUserData() {
        val ref =databaseInstance.child("Users").child(mAuth.currentUser?.uid.toString())
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user?.profileImage?.isNotEmpty()!!) {
                    GlideApp.with(requireContext())
                        .load(storgeInstance.getReference(user.profileImage!!))
                        .into(circleImageView_profile)
                } else {
                    circleImageView_profile.setImageResource(R.drawable.ic_userprofileimage)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
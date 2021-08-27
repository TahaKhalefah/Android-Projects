package com.tahadroid.wing.ui.fragments.account

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.tahadroid.wing.R
import com.tahadroid.wing.glideApp.GlideApp
import com.tahadroid.wing.models.User
import com.tahadroid.wing.ui.MainActivity
import com.tahadroid.wing.ui.intro.IntroActivity
import kotlinx.android.synthetic.main.fragment_account.*
import java.util.*


class AccountFragment : Fragment(R.layout.fragment_account), View.OnClickListener {
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
        TextViewChangeLanguage.setOnClickListener(this)
        textViewLogout.setOnClickListener(this)
        editProfileTextView.setOnClickListener(this)
        getUserData()
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
                usernameTextView.text=user.username

            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun setStatusColor() {
        val window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.white)
    }

    private fun logOut() {
        mAuth.signOut()
        sendToLogin()
    }

    private fun sendToLogin() {
        val signoutIntent = Intent(requireContext(), IntroActivity::class.java)
        signoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        signoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(signoutIntent)
    }

    private fun showPopupMenu() {
        TextViewChangeLanguage.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), TextViewChangeLanguage)
            popupMenu.menuInflater.inflate(R.menu.language_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.arabic -> {

                        setApplicationLanguage("ar")

                        val res = context?.getResources();

                        val config = res?.getConfiguration();
                        config?.locale = Locale("ar")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            config?.setLayoutDirection(Locale("ar"));
                        }
                    }
                    R.id.english -> {
                        setApplicationLanguage("en")
                        val res = context?.getResources();

                        val config = res?.getConfiguration();
                        config?.locale = Locale("en")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            config?.setLayoutDirection(Locale("en"));
                        }
                    }
                }
                true
            })
            popupMenu.show()
        }
    }

    private fun setApplicationLanguage(language: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("langauge", language)
        editor.apply()
        val res: Resources = requireActivity().resources
        val display: DisplayMetrics = res.getDisplayMetrics()
        val configuration: Configuration = res.getConfiguration()
        configuration.locale = Locale(language)
        res.updateConfiguration(configuration, display)
        val refresh = Intent(activity, MainActivity::class.java)
        refresh.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        requireActivity().startActivity(refresh)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editProfileTextView -> {
                val action = AccountFragmentDirections.actionAccountFragmentToEditProfileFragment()
                view?.findNavController()?.navigate(action)
            }
            R.id.TextViewChangeLanguage -> {
                showPopupMenu()
            }
            R.id.textViewLogout -> {
                logOut()
            }
        }
    }

}
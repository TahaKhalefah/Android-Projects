package com.tahadroid.wing.ui.intro.welcome

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tahadroid.wing.R
import com.tahadroid.wing.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap


class WelcomeActivity : AppCompatActivity(), TextWatcher {
    private  var profilePicPath: String="null"
    private val REG_STRING: String = "(?:\\s*[a-zA-Z]+){1,3}"
    private lateinit var phoneNumber: String

    companion object {
        val RES_CODE_OF_IMAGE = 1
    }

    private val storge: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }
    private val currentUserStorgeRef: StorageReference
        get() = storge.reference.child(FirebaseAuth.getInstance().currentUser?.uid.toString())
    private val currentUserDonRef: DatabaseReference
        get() = FirebaseDatabase.getInstance().reference.child("Users/${FirebaseAuth.getInstance().currentUser?.uid.toString()}")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("sharedUser", Context.MODE_PRIVATE)

        phoneNumber = intent.getStringExtra("phoneNumber").toString()
        usernameEditText.addTextChangedListener(this@WelcomeActivity)
        phonePasswordEditText.addTextChangedListener(this@WelcomeActivity)
        nextButton.setOnClickListener {
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()

            val username = usernameEditText.text.toString().trim()
            val password = phonePasswordEditText.text.toString().trim()
            editor.putString("username",username)
            editor.putString("password",password)
            editor.putString("profilePicPath",profilePicPath)
            editor.putString("phoneNumber",phoneNumber)
            editor.apply()
            if (checkValidate(username, password)) return@setOnClickListener
            createAccount(profilePicPath)
            startActivity(Intent(this@WelcomeActivity, SplashActivity::class.java))
            finish()
        }
        circleImageView_profile.setOnClickListener {
            val imageIntent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            }
            startActivityForResult(
                Intent.createChooser(imageIntent, "select Image "),
                RES_CODE_OF_IMAGE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RES_CODE_OF_IMAGE && resultCode == Activity.RESULT_OK
            && data != null && data.data != null
        ) {
            progressBar_profile.visibility = View.VISIBLE
            circleImageView_profile.setImageURI(data.data)
            val bmp = MediaStore.Images.Media.getBitmap(this.contentResolver, data.data)
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 20, stream)
            val byteImage = stream.toByteArray()
            uploadProfileImage(byteImage) { path ->
                profilePicPath = path

            }
        }
    }

    private fun createAccount(path: String) {
        val userFeildMap = mutableMapOf<String, Any>()
        userFeildMap.put("phone", phoneNumber)
        userFeildMap.put("username", usernameEditText.text.toString())
        userFeildMap.put("password", phonePasswordEditText.text.toString())
        userFeildMap.put("profileImage", path)
        currentUserDonRef.updateChildren(userFeildMap)
    }

    private fun uploadProfileImage(byteImage: ByteArray, onSuccess: (imagePath: String) -> Unit) {
        val ref = currentUserStorgeRef.child("ProfilePic/${UUID.nameUUIDFromBytes(byteImage)} ")
        ref.putBytes(byteImage).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess(ref.path)
                progressBar_profile.visibility = View.GONE
            } else {
                Toast.makeText(
                    this,
                    "Error : ${it.exception?.message.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun createAccount() {
        val rootRef = FirebaseDatabase.getInstance().reference
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (!(snapshot.child("Users").child(phoneNumber).exists())) {
                    val userData = HashMap<String, Any>()
                    userData.put("phone", phoneNumber)
                    userData.put("username", usernameEditText.text.toString())
                    userData.put("password", phonePasswordEditText.text.toString())
                    userData.put("profileImage", phoneNumber)
                    rootRef.child("Users").child(phoneNumber).updateChildren(userData)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                // u can make any thing when user registered
                            }
                        }
                } else {
                    Toast.makeText(
                        this@WelcomeActivity,
                        "This" + phoneNumber + " Already exists .",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun checkValidate(
        username: String,
        password: String
    ): Boolean {

        if (username.isEmpty()) {
            usernameEditText.error = "Enter email"
            usernameEditText.requestFocus()
            return true
        }
        if (!Pattern.matches(REG_STRING, username)) {
            usernameEditText.error = "Invalid email"
            usernameEditText.requestFocus()
            return true
        }
        if (password.isEmpty()) {
            phonePasswordEditText.error = "Enter password"
            phonePasswordEditText.requestFocus()
            return true
        }
        if (password.length < 6) {
            phonePasswordEditText.error = "Enter password more than 6 digits"
            phonePasswordEditText.requestFocus()
            return true
        }

        return false
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        nextButton.isEnabled = usernameEditText.text.trim().isNotBlank() &&
                phonePasswordEditText.text.trim().isNotBlank()

    }
}
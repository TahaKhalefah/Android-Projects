package com.example.souqadmin.login

import ahmed.adel.sleeem.clowyy.souq.pojo.request.LoginRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.request.RegisterRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.response.RegisterResponse
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.souqadmin.R
import com.example.souqadmin.databinding.ActivityRegisterBinding
import com.example.souqadmin.utils.Constants
import com.example.souqadmin.utils.Constants.USER_SHARED_PREF
import com.example.souqadmin.utils.Resource

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)

        initViewModel()
        binding.SignInRegisterTextView.setOnClickListener(this)
        binding.signUpRegisterButton.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.SignInRegisterTextView -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            binding.signUpRegisterButton -> {

                registerUser()
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    private fun registerUser() {

        val name = binding.fullNameRegisterEditText.text.toString().trim()
        val email = binding.emailRegisterEditText.text.toString().trim()
        val password = binding.passwordRegisterEditText.text.toString().trim()
        val registerRequest = RegisterRequest(name, email, password)
        viewModel.registerUser(registerRequest)

        viewModel.register.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }
                Resource.Status.SUCCESS -> {
                    it.data.let { response ->
                        Log.e("sssss", response.toString())
                        saveUserInfo(registerRequest)
                        saveUserInfo(response!!)
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        })
    }

    fun saveUserInfo(registerRequest: RegisterRequest) {
        val sharedPreferences = getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", registerRequest.email)
        editor.putString("password", registerRequest.password)
        editor.putString("name", registerRequest.companyName)
        editor.apply()
    }


    fun saveUserInfo(response: RegisterResponse) {
        val editor = sharedPreferences.edit()
        editor.putString("id", response._id)
        editor.putString("email", response.email)
        editor.putString("profileImage", response.profileImage)
        editor.apply()
    }
}
package com.example.souqadmin.login

import ahmed.adel.sleeem.clowyy.souq.pojo.request.LoginRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.response.LoginResponse
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.souqadmin.HomeActivity
import com.example.souqadmin.R
import com.example.souqadmin.databinding.ActivityLoginBinding
import com.example.souqadmin.utils.Constants
import com.example.souqadmin.utils.Constants.USER_SHARED_PREF
import com.example.souqadmin.utils.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "LoginActivity"
    private val RC_SIGN_IN = 121
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginResponse: LoginResponse
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onStart() {
        super.onStart()

        Log.i(TAG, getLogin().toString())
        // If user logged in, go directly to ProductActivity
        if (getLogin()) {
            goToSplashActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)


//        val email = sharedPreferences.getString("email" , null)
//        val password =  sharedPreferences.getString("password" , null)
//        binding.emailLoginEditText.setText(email)
//        binding.passwordLoginEditText.setText(password)

        initViewModel()

        binding.RegisterLoginTextView.setOnClickListener(this)
        binding.signInLoginButton.setOnClickListener(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.RegisterLoginTextView -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
            binding.signInLoginButton -> {
                loginUser()
            }

        }
    }


    fun loginUser() {

        val email = binding.emailLoginEditText.text.toString().trim()
        val password = binding.passwordLoginEditText.text.toString().trim()
        val loginRequist = LoginRequest(email, password)
        // viewModel.loginUser(loginRequist, token!!)
        viewModel.loginUser(loginRequist)

        viewModel.login.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        loginResponse = it!!
                        saveUserInfo(loginResponse)
                        setLogin(true)
                        goToSplashActivity()
                    }
                }
            }
        })
    }

    fun setLogin(isLogin: Boolean) {
        val sharedPreferences = this.getSharedPreferences(
            Constants.USER_SHARED_PREF,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLogin", isLogin)
        editor.apply()
    }

    private fun goToSplashActivity() {
        startActivity(
            Intent(this@LoginActivity, HomeActivity::class.java)
        )
        finish()
    }

    fun getLogin(): Boolean {
        val sharedPreferences = this.getSharedPreferences(
            Constants.USER_SHARED_PREF,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean("isLogin", false)
    }

    fun isLoggedIn(isLogin: Boolean): Boolean {
        val sharedPreferences =
            this.getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString("id", null) != null && isLogin
    }

    fun saveUserInfo(loginResponse: LoginResponse) {
        val editor = sharedPreferences.edit()
        editor.putString("error", loginResponse.error)
        editor.putString("result", loginResponse.result)
        editor.putString("token", loginResponse.token)
        editor.putString("id", loginResponse.id)
        editor.apply()
    }

}
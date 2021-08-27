package com.tahadroid.bestrepo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tahadroid.bestrepo.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
   private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goToMain()
    }

    private fun goToMain() {
        Handler().postDelayed({
            val mIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(mIntent)
            finish()
        }, 3000)
    }
}
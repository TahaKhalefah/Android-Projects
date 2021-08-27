package com.tahadroid.wing.ui.splash

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tahadroid.wing.R
import com.tahadroid.wing.ui.MainActivity
import java.util.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setStatusColor()
        setApplicationLanguage()
        goToMain()
    }
    private fun goToMain() {
        Handler().postDelayed({
            val mIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(mIntent)
            finish()
        }, 2000)
    }
    private fun setStatusColor() {
        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }
    private fun setApplicationLanguage() {
        val sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        var language = sharedPreferences.getString("langauge", "")
        if (language == "") language = "en"
        val res: Resources = this.resources
        val display: DisplayMetrics = res.getDisplayMetrics()
        val configuration: Configuration = res.getConfiguration()
        configuration.locale = Locale(language)
        res.updateConfiguration(configuration, display)
    }
}
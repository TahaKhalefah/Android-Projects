package com.tahadroid.aflam4u.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tahadroid.aflam4u.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView:BottomNavigationView =findViewById(R.id.nav_view)
        val navController =findNavController(R.id.nav_host)
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.popularFragment,R.id.detailsFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}

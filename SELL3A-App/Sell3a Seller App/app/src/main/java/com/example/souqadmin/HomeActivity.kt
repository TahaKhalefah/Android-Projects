package com.example.souqadmin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.souqadmin.login.LoginActivity
import com.example.souqadmin.login.RegisterActivity
import com.example.souqadmin.utils.Constants.USER_SHARED_PREF

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        val header = navView.getHeaderView(0) as LinearLayout
        val userName = header.findViewById(R.id.companyName) as TextView
        val userEmail = header.findViewById(R.id.user_email) as TextView
        sharedPreferences = getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        userName.text = sharedPreferences.getString("name","company name ")
        userEmail.text = sharedPreferences.getString("email","user@gmail.com ")

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow , R.id.ordersFragment, R.id.soldOutFragment, R.id.productFragment
            ), drawerLayout
        )
        navView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener({
            clearAll()
            startActivity(Intent(baseContext, LoginActivity::class.java))
            finish();
            true
        });
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun clearAll() {
        val sharedPreferences = getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear().apply()
        editor.apply()
    }
}
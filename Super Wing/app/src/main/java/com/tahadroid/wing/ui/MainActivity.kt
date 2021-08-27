package com.tahadroid.wing.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.tahadroid.wing.R
import com.tahadroid.wing.data.local.OrderDatabase
import com.tahadroid.wing.repository.Repository
import com.tahadroid.wing.ui.fragments.basket.BasketViewModel
import com.tahadroid.wing.ui.fragments.basket.BasketViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    private lateinit var basketViewModel: BasketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setupWithNavController(news_nav_host_fragment.findNavController())
        initViewModel()


            basketViewModel.getAll().observe(this@MainActivity, Observer {
                if(it.size > 0){
                    nav_view.getOrCreateBadge(R.id.basketFragment).apply {
                        number=it.size
                    }
                }else{
                    nav_view.removeBadge(R.id.basketFragment)
                }
            })


        nav_view.getOrCreateBadge(R.id.notificationFragment).apply {
                number =0
        }
    }

    private fun initViewModel() {
        val repository = Repository(OrderDatabase(this))
        val checkoutViewModelFactory = BasketViewModelFactory(repository)
        basketViewModel =
            ViewModelProvider(this, checkoutViewModelFactory)
                .get(BasketViewModel::class.java)
    }

}
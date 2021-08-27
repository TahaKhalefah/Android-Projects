package com.tahadroid.wing.ui.fragments.home.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.tahadroid.wing.R
import com.tahadroid.wing.models.Shop
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var searchActivityAdapter: SearchActivityAdapter
    private var list: ArrayList<Shop>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if(list.isNullOrEmpty() ) {
            list = intent.getParcelableArrayListExtra("myList")
            setRecyclerView(list)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null)
                        searchActivityAdapter.filter(newText)

                    return false
                }

            })
        }
    }

    private fun setRecyclerView(list: ArrayList<Shop>?) {
        searchActivityAdapter = SearchActivityAdapter { view, restaurant, i ->
        }
        searchActivityAdapter.swapData(list!!)
        lv1.apply {
            adapter = searchActivityAdapter
        }
    }


    override fun onClick(v: View) {
    }

    override fun onStop() {
        super.onStop()
        if(list?.size!! > 0){
            list?.clear()
        }

    }
}
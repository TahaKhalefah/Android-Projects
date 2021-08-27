package com.tahadroid.bestrepo.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tahadroid.bestrepo.databinding.ActivityMainBinding
import com.tahadroid.bestrepo.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var repoAdapter: RepoAdapter
    private lateinit var viewModel: RepoViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RepoViewModel::class.java)
        initRecyclerView()
        observeData()

    }



    private fun observeData() {
        viewModel.getRepos().observe(this, Observer {
          when(it){
              is Resource.Success -> {
                  hideProgressBar()
                  repoAdapter.swapData(it.data!!.items)
              }
              is Resource.Error -> {
                  hideProgressBar()
                  it.message?.let { message ->

                  }
              }
              is Resource.Loading -> {
                  showProgressBar()
              }
          }
        })
    }

    //pagination
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            //count items show in screen
            val visibleItemCount = layoutManager.childCount
            // total item count in RV
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 30
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getRepos()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        isLoading = true
    }
    private fun initRecyclerView() {
        repoAdapter = RepoAdapter{
                _, _, _ ->
        }

        binding.reposRecyclerView.apply {
            adapter=repoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addOnScrollListener(this@MainActivity.scrollListener)
        }
    }

}
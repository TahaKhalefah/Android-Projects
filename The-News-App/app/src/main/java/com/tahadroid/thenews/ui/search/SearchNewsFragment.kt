package com.tahadroid.thenews.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tahadroid.thenews.R
import com.tahadroid.thenews.adapters.NewsAdapter
import com.tahadroid.thenews.ui.NewsActivity
import com.tahadroid.thenews.utills.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.tahadroid.thenews.utills.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private lateinit var searchAdapter: NewsAdapter
    private lateinit var searchViewModel: SearchNewsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel = (activity as NewsActivity).searchViewModel
        setupRecyclerView()
        searchAdapter.setOnItemClickListener {
            val bundle =Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_navigation_search_to_articleFragment,bundle)
        }
        var job: Job? = null
        search_edit_text.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        searchViewModel.getSearchNews(editable.toString())
                    }
                }
            }
        }

        searchViewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        searchAdapter.differ.submitList(it.articles)
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        searchAdapter = NewsAdapter()
        search_news_rv.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
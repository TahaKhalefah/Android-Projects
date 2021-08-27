package com.tahadroid.thenews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.tahadroid.thenews.R
import com.tahadroid.thenews.data.local.ArticleDatabase
import com.tahadroid.thenews.repository.NewsRepository
import com.tahadroid.thenews.ui.article.ArticleViewModel
import com.tahadroid.thenews.ui.article.ArticleViewModelFactory
import com.tahadroid.thenews.ui.news.BreakingNewsViewModel
import com.tahadroid.thenews.ui.news.BreakingNewsViewModelFactory
import com.tahadroid.thenews.ui.save.SaveNewsViewModel
import com.tahadroid.thenews.ui.save.SaveNewsViewModelFactory
import com.tahadroid.thenews.ui.search.SearchNewsViewModel
import com.tahadroid.thenews.ui.search.SearchNewsViewModelFactory
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {
    lateinit var saveViewModel: SaveNewsViewModel
    lateinit var articleViewModel: ArticleViewModel
    lateinit var viewModel: BreakingNewsViewModel
    lateinit var searchViewModel: SearchNewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val repository = NewsRepository(ArticleDatabase(this))

        val breakingViewModelProviderFactory = BreakingNewsViewModelFactory(repository)
        viewModel = ViewModelProvider(
            this,
            breakingViewModelProviderFactory
        ).get(BreakingNewsViewModel::class.java)

        val searchViewModelProviderFactory = SearchNewsViewModelFactory(repository)
        searchViewModel = ViewModelProvider(
            this,
            searchViewModelProviderFactory
        ).get(SearchNewsViewModel::class.java)

        val articleViewModelProviderFactory = ArticleViewModelFactory(repository)
        articleViewModel = ViewModelProvider(
            this,
            articleViewModelProviderFactory
        ).get(
            ArticleViewModel::class.java
        )

        val saveViewModelFactory = SaveNewsViewModelFactory(repository)
        saveViewModel = ViewModelProvider(
            this,
            saveViewModelFactory
        ).get(
            SaveNewsViewModel::class.java
        )
        nav_view.setupWithNavController(news_nav_host_fragment.findNavController())
    }
}
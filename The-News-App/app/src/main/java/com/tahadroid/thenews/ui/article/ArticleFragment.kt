package com.tahadroid.thenews.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.tahadroid.thenews.R
import com.tahadroid.thenews.ui.NewsActivity
import com.tahadroid.thenews.ui.news.BreakingNewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment() {
    val argument: ArticleFragmentArgs by navArgs()

    private lateinit var articleViewModel: ArticleViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        articleViewModel = (activity as NewsActivity).articleViewModel

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = argument.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
        add_floatingActionButton.setOnClickListener {
            articleViewModel.addArticle(article)
            Snackbar.make(view,"added", Snackbar.LENGTH_LONG).show()
        }
    }
}
package com.tahadroid.thenews.ui.save

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tahadroid.thenews.R
import com.tahadroid.thenews.adapters.NewsAdapter
import com.tahadroid.thenews.ui.NewsActivity
import kotlinx.android.synthetic.main.fragment_save_news.*

class SaveNewsFragment : Fragment(R.layout.fragment_save_news) {

    private lateinit var saveAdapter: NewsAdapter
    private lateinit var saveNewsViewModel: SaveNewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveNewsViewModel = (activity as NewsActivity).saveViewModel
        setupRecyclerView()
        saveAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_navigation_save_to_articleFragment, bundle)
        }
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                saveNewsViewModel.deleteArticle(saveAdapter.differ.currentList[position])
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        saveNewsViewModel.addArticle(saveAdapter.differ.currentList[position])
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(save_news_rv)
        }
        saveNewsViewModel.getAllArticles().observe(viewLifecycleOwner, Observer {
            saveAdapter.differ.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        saveAdapter = NewsAdapter()
        save_news_rv.apply {
            adapter = saveAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
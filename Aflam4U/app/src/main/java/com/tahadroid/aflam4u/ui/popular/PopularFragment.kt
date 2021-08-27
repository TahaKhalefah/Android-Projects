package com.tahadroid.aflam4u.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tahadroid.aflam4u.R
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_popular.*

/**
 * A simple [Fragment] subclass.
 */
class PopularFragment : Fragment() {


    lateinit var subscription: @NonNull Disposable
    private lateinit var popularViewModel: PopularViewModel
    private lateinit var adapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        popularViewModel = ViewModelProvider(this).get(PopularViewModel::class.java)
        setupObserver()
        setupRecyclerView()
    }

    private fun setupObserver() {
        subscription = popularViewModel.getPopular().subscribe(
            {
                if (it.results.isNotEmpty()) {
                    adapter.swapData(it.results)
                }
            }, {

            }
        )
    }

    private fun setupRecyclerView() {
        popular_rv.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PopularAdapter { view, resultsItem, i ->
            //send args
            val action =
                PopularFragmentDirections.actionPopularFragmentToDetailsFragment(resultsItem.id)
            findNavController().navigate(action)
        }

        popular_rv.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        if (subscription.isDisposed)
            subscription.dispose()
    }
}

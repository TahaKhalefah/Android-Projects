package com.tahadroid.aflam4u.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.tahadroid.aflam4u.R
import com.tahadroid.aflam4u.pojo.details.DetailsResponse
import com.tahadroid.aflam4u.utils.IMAGE_BASE_URL
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_details.*


/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : Fragment() {

    lateinit var subscription:  Disposable
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var detailsViewModel: DetailsViewModel

    //get arg
    val arguments: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        setupObserver()
    }

    private fun setupObserver() {
      subscription = detailsViewModel.Details(arguments.movieId).subscribe({
          setupViews(it)
      },{

      })
    }

    private fun setupViews(data: DetailsResponse) {
        setupRecyclerView()
        Glide.with(this).load("$IMAGE_BASE_URL${data.posterPath}").into(poster_details_iv)
        Glide.with(this).load("$IMAGE_BASE_URL${data.backdropPath}").into(backdrop_iv)
        genresAdapter.swapData(data.genres)
        title_tv.text = data.title
        rate_tv.text = data.voteAverage.toString()
        language_tv.text = data.originalLanguage
        date_tv.text = data.releaseDate
        overview_tv.text = data.overview
    }

    private fun setupRecyclerView() {
        genresAdapter = GenresAdapter { view, genresItem, i ->

        }
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_END
        genres_rv.layoutManager = layoutManager
        genres_rv.adapter = genresAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        if(subscription.isDisposed)
        subscription.dispose()
    }
}

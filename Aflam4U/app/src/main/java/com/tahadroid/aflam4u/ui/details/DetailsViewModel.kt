package com.tahadroid.aflam4u.ui.details

import androidx.lifecycle.ViewModel
import com.tahadroid.aflam4u.pojo.details.DetailsResponse
import com.tahadroid.aflam4u.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsViewModel : ViewModel() {
    private val repository = MovieRepository()

    fun Details(movieId: Int):Single<DetailsResponse> {
        return repository.getDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }
}
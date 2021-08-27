package com.tahadroid.aflam4u.ui.popular

import androidx.lifecycle.ViewModel
import com.tahadroid.aflam4u.pojo.popular.PopularResponse
import com.tahadroid.aflam4u.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PopularViewModel : ViewModel() {
    private val repo = MovieRepository()
    fun getPopular(): @NonNull Single<PopularResponse> {
        return  repo.getPopular().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}
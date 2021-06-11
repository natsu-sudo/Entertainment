package com.coding.entertainmentlist.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.coding.entertainmentlist.database.TvSeries
import com.coding.entertainmentlist.database.TvSeriesDetailRepo

class DetailViewModel(context: Context): ViewModel() {
    private var mutableId=MutableLiveData<Long>()
    
    private val detailRepo=TvSeriesDetailRepo(context)

    val movieId:LiveData<Long>
    get() = mutableId

    fun setMovieId(id:Long):Unit{
        mutableId.value=id
    }

    val movieDetail:LiveData<TvSeries> =Transformations.switchMap(mutableId,::getMovieDetail)

    private fun getMovieDetail(id: Long): LiveData<TvSeries> {
            return detailRepo.getDetail(id)
    }


}
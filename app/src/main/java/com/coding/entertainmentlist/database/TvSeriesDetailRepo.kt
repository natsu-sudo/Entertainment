package com.coding.entertainmentlist.database

import android.content.Context
import androidx.lifecycle.LiveData


class TvSeriesDetailRepo(context: Context) {
    private val tvDetails=TvSeriesDatabase.getInstance(context).tvSeriesDetailDao()

    fun getDetail(id:Long):LiveData<TvSeries>{
        return tvDetails.getTvSeriesDetail(id)
    }







}
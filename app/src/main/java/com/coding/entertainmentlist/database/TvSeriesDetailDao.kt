package com.coding.entertainmentlist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface TvSeriesDetailDao {

    @Query("Select * from tv_series where id ==:id ")
    fun getTvSeriesDetail(id:Long):LiveData<TvSeries>
}
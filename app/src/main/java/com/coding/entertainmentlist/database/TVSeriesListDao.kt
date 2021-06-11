package com.coding.entertainmentlist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.selects.select

@Dao
interface TVSeriesListDao {
    @Query("select * from tv_series order by first_air_date desc")
    fun getTvSeriesList():LiveData<List<TvSeries>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIntoDb(list: List<TvSeries>)

    @Query("delete from tv_series")
    suspend fun deleteAllData()
}
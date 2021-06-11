package com.coding.entertainmentlist.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.coding.entertainmentlist.network.TmdbService
import com.coding.entertainmentlist.pojo.ErrorCode
import com.coding.entertainmentlist.pojo.LoadingStatus
import java.lang.Exception
import java.net.UnknownHostException

class TvSeriesListRepository(context: Context) {
    private val tvDatabase=TvSeriesDatabase.getInstance(context).tvSeriesListDao()
    private val tmdbService by lazy { TmdbService.getInstance() }

    fun getTVSeriesList():LiveData<List<TvSeries>>{
        return tvDatabase.getTvSeriesList()
    }

    suspend fun insertListIntoDb(list: List<TvSeries>){
        tvDatabase.insertIntoDb(list)
    }

    suspend fun deleteFromDataBase(){
        tvDatabase.deleteAllData()
    }

    suspend fun fetchFromNetwork()=try {
        val result=tmdbService.getTvSeriesFromNetwork()
        Log.d("TAG", "fetchFromNetwork: "+result)
        if (result.isSuccessful){
            val tvSeriesList=result.body()
            Log.d("TAG", "fetchFromNetwork: "+tvSeriesList)
            tvSeriesList?.let {
                Log.d("TAG", "fetchFromNetwork: "+it.results)
                tvDatabase.insertIntoDb(it.results) }
            LoadingStatus.success()
        }else{
            LoadingStatus.error(ErrorCode.NO_DATA)
        }
    }catch (ex:UnknownHostException){
        LoadingStatus.error(ErrorCode.NETWORK_ERROR)
    }catch (ex:Exception){
        LoadingStatus.error(ErrorCode.UNKNOWN_ERROR)
    }

}
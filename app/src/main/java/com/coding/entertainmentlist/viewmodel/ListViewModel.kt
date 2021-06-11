package com.coding.entertainmentlist.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.entertainmentlist.database.TvSeries
import com.coding.entertainmentlist.database.TvSeriesListRepository
import com.coding.entertainmentlist.pojo.LoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(context: Context):ViewModel() {
    private val tvSeriesListRepo =TvSeriesListRepository(context)
    private var liveStatus=MutableLiveData<LoadingStatus>()
    val status get() = liveStatus

    val getList:LiveData<List<TvSeries>> = getTvList()

    private fun getTvList(): LiveData<List<TvSeries>> {
        return tvSeriesListRepo.getTVSeriesList()
    }

    fun fetchFromNetwork(){
        liveStatus.value=LoadingStatus.loading()
        viewModelScope.launch {
            liveStatus.value = withContext(Dispatchers.IO){
                tvSeriesListRepo.fetchFromNetwork()
            }!!
        }
    }

    fun deleteData() {
        viewModelScope.launch {
            tvSeriesListRepo.deleteFromDataBase()
        }
    }
}
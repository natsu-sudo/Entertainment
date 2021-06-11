package com.coding.entertainmentlist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@TypeConverters(DBTypeConverter::class)
@Database(entities = [TvSeries::class],version = 1)
abstract class TvSeriesDatabase: RoomDatabase() {
    abstract fun tvSeriesListDao():TVSeriesListDao
    abstract fun tvSeriesDetailDao():TvSeriesDetailDao

    companion object{
        @Volatile
        private var instance:TvSeriesDatabase?=null

        fun getInstance(context: Context)= instance?:
        synchronized(this){
            Room.databaseBuilder(context.applicationContext,TvSeriesDatabase::class.java,"table_tv").build().also {
                instance=it
            } }

        }

    }



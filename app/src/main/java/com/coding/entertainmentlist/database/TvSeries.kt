package com.coding.entertainmentlist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "tv_series")
data class TvSeries(
    @PrimaryKey
    val id:Long,
    @ColumnInfo( name = "backdrop_path")
    @SerializedName("backdrop_path")
    val backdropPath:String,
    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    val firstAirDate:Date,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val title:String,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val posterPath:String,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overView:String,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val rating:Double
)
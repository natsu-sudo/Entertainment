package com.coding.entertainmentlist.network

import com.coding.entertainmentlist.BuildConfig
import com.coding.entertainmentlist.pojo.TmdbServiceList
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TmdbService {

    companion object{
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185"
        const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w300"

        private val retrofitService by lazy {
            val interceptor=Interceptor{
                val request=it.request()
                val url=request.url().newBuilder()
                    .addQueryParameter("api_key",BuildConfig.TMDB_API_KEY)
                    .build()
                val newRequest=request.newBuilder()
                    .url(url)
                    .build()
                it.proceed(newRequest)
            }

            val httpClient=OkHttpClient().newBuilder().addInterceptor(interceptor).build()
            val gson=GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(TmdbService::class.java)
        }

        fun getInstance():TmdbService= retrofitService
    }
    @GET("discover/tv?language=en-US&sort_by=popularity.desc&page=1&timezone=America%2FNew_York&include_null_first_air_dates=false&with_watch_monetization_types=flatrate")
    suspend fun getTvSeriesFromNetwork():Response<TmdbServiceList>
}
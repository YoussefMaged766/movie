package com.example.movie.util

import com.example.movie.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface webservices {

    @GET("movie/top_rated?")
    fun getTopRatedmovies(@Query("api_key") key:String ,
                          @Query("language") language:String,
                          @Query("page") page:Int=1
                            ):Call<TopRatedResponse>

    @GET("movie/upcoming?")
    fun getupcomingmovies(@Query("api_key") key:String ,
                          @Query("language") language:String,
                          @Query("page") page:Int=1
    ):Call<TopRatedResponse>
    @GET("movie/popular?")
    fun getpopularmovies(@Query("api_key") key:String ,
                          @Query("language") language:String,
                          @Query("page") page:Int=1
    ):Call<TopRatedResponse>

    @GET("movie/{movie_id}/videos?")
    fun get_trailer(@Path("movie_id") id: Int?, @Query("api_key") key:String,
                    @Query("language") language:String
    ):Call<TrailerResponse>

    @GET("movie/{movie_id}/recommendations?")
    fun get_recommended(@Path("movie_id") id: Int?, @Query("api_key") key:String,
                    @Query("language") language:String
    ):Call<RecommendedResponse>

    @GET("trending/movie/day?")
    fun get_trend_movie( @Query("api_key") key:String,
                        @Query("language") language:String
    ):Call<TrendResponse>

    @GET("trending/tv/day?")
    fun get_trend_tv( @Query("api_key") key:String,
                         @Query("language") language:String
    ):Call<TrendtvResponse>
}
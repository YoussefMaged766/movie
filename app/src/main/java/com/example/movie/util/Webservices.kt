package com.example.movie.util

import com.example.movie.models.RecommendedResponse
import com.example.movie.models.TopRatedResponse
import com.example.movie.models.TrailerResponse
import com.example.movie.models.TrendResponse
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

    @GET("trending/{media_type}/day?")
    fun get_trend(@Path("media_type") type: String?, @Query("api_key") key:String,
                        @Query("language") language:String
    ):Call<TrendResponse>
}
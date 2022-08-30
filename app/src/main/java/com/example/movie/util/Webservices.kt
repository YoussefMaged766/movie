package com.example.movie.util

import com.example.movie.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface webservices {

    @GET("movie/top_rated?")
    fun getTopRatedmovies(
    ): Call<TopRatedResponse>

    @GET("movie/top_rated?")
    suspend fun getTopRatedmoviespaging(
        @Query("page") page: Int
    ): TopRatedResponse


    @GET("movie/upcoming?")
    fun getupcomingmovies(
        @Query("page") page: Int
    ): Call<TopRatedResponse>

    @GET("movie/popular?")
    fun getpopularmovies(

    ): Call<TopRatedResponse>

    @GET("movie/{movie_id}/videos?")
    fun get_trailer(
        @Path("movie_id") id: Int?,
    ): Call<TrailerResponse>

    @GET("tv/{tv_id}/videos?")
    fun get_trailer_tv(
        @Path("tv_id") id: Int?
    ): Call<TrailerResponse>

    @GET("movie/{movie_id}/recommendations?")
    fun get_recommended(
        @Path("movie_id") id: Int?
    ): Call<TopRatedResponse>

    @GET("trending/movie/day?")
    fun get_trend_movie(
    ): Call<TopRatedResponse>

    @GET("trending/tv/day?")
    fun get_trend_tv(
    ): Call<TrendtvResponse>

    @GET("discover/movie?")
    fun getmovies_by_category(
        @Query("with_genres") genre: Int
    ): Call<TopRatedResponse>

    @GET("search/movie?")
    fun getserchmovies(
        @Query("query") query: String
    ): Call<TopRatedResponse>
}
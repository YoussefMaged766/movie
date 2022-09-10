package com.example.movie.util

import com.example.movie.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServices {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int? = null
    ): TopRatedResponse

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int? = null
    ): TopRatedResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int? = null
    ): TopRatedResponse

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
    suspend fun get_trend_movie(
        @Query("page") page: Int? = null
    ): TopRatedResponse

    @GET("trending/tv/day?")
    suspend fun get_trend_tv(
        @Query("page") page: Int? = null
    ): TrendtvResponse

    @GET("discover/movie?")
    fun getmovies_by_category(
        @Query("with_genres") genre: Int
    ): Call<TopRatedResponse>

    @GET("search/movie?")
    fun getserchmovies(
        @Query("query") query: String
    ): Call<TopRatedResponse>
}
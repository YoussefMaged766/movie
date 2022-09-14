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
    suspend fun get_trailer(
        @Path("movie_id") id: Int?,
    ): TrailerResponse

    @GET("tv/{tv_id}/videos?")
    suspend fun get_trailer_tv(
        @Path("tv_id") id: Int?
    ): TrailerResponse

    @GET("movie/{movie_id}/recommendations?")
    suspend fun get_recommended(
        @Path("movie_id") id: Int?,
        @Query("page") page: Int? = null
    ): TopRatedResponse

    @GET("trending/movie/day?")
    suspend fun get_trend_movie(
        @Query("page") page: Int? = null
    ): TopRatedResponse

    @GET("trending/tv/day?")
    suspend fun get_trend_tv(
        @Query("page") page: Int? = null
    ): TrendtvResponse

    @GET("discover/movie?")
    suspend fun getmovies_by_category(
        @Query("with_genres") genre: Int? = null,
        @Query("page") page: Int? = null
    ): TopRatedResponse

    @GET("search/movie?")
    suspend fun getserchmovies(
        @Query("query") query: String,
        @Query("page") page: Int? = null
    ): TopRatedResponse

    @GET("tv/{tv_id}")
    suspend fun getTVDetails(
        @Path("tv_id") id: Int
    ): TVDetailesResponse

    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int
    ): TVSeasonsDetailsResponse

    @GET("tv/{tv_id}/season/{season_number}/videos")
    suspend fun getSeasonTrailer(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int
    ): TrailerResponse


}
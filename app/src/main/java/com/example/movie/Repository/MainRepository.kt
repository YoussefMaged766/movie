package com.example.movie.Repository

import com.example.movie.models.TopRatedResponse
import com.example.movie.util.WebServices

class MainRepository(val webservices: WebServices) {
    suspend fun fetchTopRatedMovies() = webservices.getTopRatedMovies()
    suspend fun fetchPopularMovies() = webservices.getPopularMovies()
    suspend fun fetchUpComingMovies() = webservices.getUpComingMovies()

    suspend fun fetchTVDetailes(id: Int) = webservices.getTVDetails(id)

    suspend fun fetchSeasonsDetails(tvId: Int, seasonNumber: Int) =
        webservices.getSeasonDetails(tvId, seasonNumber)

    suspend fun fetchCrewDetails(tvId: Int, seasonNumber: Int) =
        webservices.getCrewDetails(tvId, seasonNumber)

    suspend fun fetchTVTrailer(id: Int) = webservices.get_trailer_tv(id)


}
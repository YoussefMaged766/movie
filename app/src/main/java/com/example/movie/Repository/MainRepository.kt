package com.example.movie.Repository

import com.example.movie.models.TopRatedResponse
import com.example.movie.util.WebServices

class MainRepository(val webservices: WebServices) {
    suspend fun fetchTopRatedMovies() = webservices.getTopRatedMovies()
    suspend fun fetchPopularMovies() = webservices.getPopularMovies()
    suspend fun fetchUpComingMovies() = webservices.getUpComingMovies()



}
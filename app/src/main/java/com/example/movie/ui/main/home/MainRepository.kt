package com.example.movie.ui.main.home

import com.example.movie.models.TopRatedResponse
import com.example.movie.util.webservices

class MainRepository(val webservices: webservices) {
    suspend fun fetchhMovies():TopRatedResponse {
       return webservices.getTopRatedmovies()
    }
}
package com.example.movie.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movie.Repository.MainRepository
import com.example.movie.util.Resource
import com.example.movie.util.apimanager
import kotlinx.coroutines.Dispatchers

class MainViewModel():ViewModel() {
    private val mainRepository: MainRepository = MainRepository(apimanager.getwebbservices())
   suspend fun getTopRatedMovies() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchTopRatedMovies()))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }
    suspend fun getUpComingMovies() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchUpComingMovies()))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }
    suspend fun getPopularMovies() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchPopularMovies()))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }

    suspend fun getMovieTrailer(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchTrailerMovies(id)))

        } catch (e: Exception) {
            emit(Resource.error(null,e.message.toString()))
        }
    }

}
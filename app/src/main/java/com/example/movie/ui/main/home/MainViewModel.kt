package com.example.movie.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movie.Repository.MainRepository
import com.example.movie.util.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository):ViewModel() {
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

}
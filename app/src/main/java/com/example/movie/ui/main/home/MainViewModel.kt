package com.example.movie.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movie.Repository.MoviesRepo
import com.example.movie.util.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository):ViewModel() {
   suspend fun getMovies() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchhMovies()))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }
}
package com.example.movie.ui.main.detailed.tv


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movie.Repository.MainRepository
import com.example.movie.util.Resource
import kotlinx.coroutines.Dispatchers

class TVViewModel(val mainRepository: MainRepository) : ViewModel() {
    suspend fun getTVDetailes(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchTVDetailes(id = id)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }

    suspend fun getTVTrailer(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchTVTrailer(id = id)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }

    suspend fun getSeasonDetails(id: Int, number: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchSeasonsDetails(id, number)))

        } catch (e: Exception) {
            emit(Resource.error(null,e.message.toString()))
        }
    }

    suspend fun getSeasonTrailer(id: Int, number: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchSeasonTrailer(id, number)))

        } catch (e: Exception) {
            emit(Resource.error(null,e.message.toString()))
        }
    }
    suspend fun getEpisodesTrailer(id: Int, seasonNumber: Int ,episodesNumber:Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchEpisodesTrailer(id, seasonNumber,episodesNumber)))

        } catch (e: Exception) {
            emit(Resource.error(null,e.message.toString()))
        }
    }

    suspend fun getEpisodesDetails(id: Int, seasonNumber: Int ,episodesNumber:Int ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchEpisodesDetails(id, seasonNumber , episodesNumber)))

        } catch (e: Exception) {
            emit(Resource.error(null,e.message.toString()))
        }
    }

}
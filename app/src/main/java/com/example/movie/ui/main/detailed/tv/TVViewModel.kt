package com.example.movie.ui.main.detailed.tv


import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movie.Repository.MainRepository
import com.example.movie.util.Resource
import kotlinx.coroutines.Dispatchers

class TVViewModel(val mainRepository: MainRepository):ViewModel() {
    suspend fun getTVDetailes(id:Int)= liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchTVDetailes(id = id)))
        } catch (e:Exception){
            emit(Resource.error(null,e.message.toString()))
        }
    }

    suspend fun getTVTrailer(id:Int)= liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchTVTrailer(id = id)))
        } catch (e:Exception){
            emit(Resource.error(null,e.message.toString()))
        }
    }
}
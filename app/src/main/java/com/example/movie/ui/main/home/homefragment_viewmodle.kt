package com.example.movie.ui.main.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie.Repository.MoviesRepo


import com.example.movie.models.TopRatedResponse
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import com.example.movie.models.movie
import com.example.movie.ui.main.toprated.MoviesPagingSource

import com.example.movie.util.webservices
import kotlinx.coroutines.flow.Flow

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class homefragment_viewmodel : ViewModel() {


    lateinit var response_toprated: MutableLiveData<List<movie?>>
    lateinit var response_upcoming: MutableLiveData<List<movie?>>
    lateinit var response_popular: MutableLiveData<List<movie?>>

    var pages_toprated: MutableLiveData<Int> = MutableLiveData()
    var pages_upcoming: MutableLiveData<Int> = MutableLiveData()
    var pages_popular: MutableLiveData<Int> = MutableLiveData()

init {
    getPopularData()
    getTopRatedData()
    getUpComingData()
}







    fun getdatafromapi_toprated() {


    }

    fun getdatafromapi_upcoming() {


    }

    fun getdatafromapi_poppular() {


    }

//    fun get_data(): MutableLiveData<List<movie?>> {
//        return response_toprated
//    }

    fun getTopRatedData(){
        response_toprated = MoviesRepo.getTopRatedMovies()
    }
    fun getUpComingData(){
        response_upcoming = MoviesRepo.getUpComingMovies()
    }
    fun  getPopularData(){
        response_popular = MoviesRepo.getPopularMovies()
    }


    fun getListData(): Flow<PagingData<movie>> {
        return Pager (config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = {MoviesPagingSource(apimanager.getwebbservices())}).flow.cachedIn(viewModelScope)
    }

}
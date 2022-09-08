package com.example.movie.ui.main.home


import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie.util.apimanager
import com.example.movie.models.movie
import com.example.movie.ui.main.toprated.MoviePopularPagingSource
import com.example.movie.ui.main.toprated.MovieUpComingPagingSource
import com.example.movie.ui.main.toprated.MoviesPagingSource
import com.example.movie.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow



class homefragment_viewmodel : ViewModel() {

    init {

        getListDataTopRated()
        getListDataPopular()
        getListDataUpComing()
    }



    fun getListDataTopRated(): Flow<PagingData<movie>> {
        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { MoviesPagingSource(apimanager.getwebbservices()) }).flow.cachedIn(
            viewModelScope
        )
    }

    fun getListDataPopular(): Flow<PagingData<movie>> {

        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { MoviePopularPagingSource(apimanager.getwebbservices()) }).flow.cachedIn(
            viewModelScope
        )


    }

    fun getListDataUpComing(): Flow<PagingData<movie>> {
        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { MovieUpComingPagingSource(apimanager.getwebbservices()) }).flow.cachedIn(
            viewModelScope
        )
    }




}
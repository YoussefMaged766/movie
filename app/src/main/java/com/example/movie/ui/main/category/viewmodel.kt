package com.example.movie.ui.main.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie.models.TopRatedResponse
import com.example.movie.models.TrailerResponse
import com.example.movie.models.movie
import com.example.movie.ui.main.toprated.MoviesPagingSource
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class viewmodel : ViewModel() {


    fun getListDataCategory(id: Int): Flow<PagingData<movie>> {
        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { MoviesByCategoryPagingSource(id,apimanager.getwebbservices()) }).flow.cachedIn(
            viewModelScope
        )
    }
}
package com.example.movie.ui.main.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie.models.TopRatedResponse
import com.example.movie.models.movie
import com.example.movie.ui.main.category.MoviesByCategoryPagingSource
import com.example.movie.util.apimanager
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel : ViewModel() {
    var response_search: MutableLiveData<List<movie?>> = MutableLiveData()
    var error : MutableLiveData<String> = MutableLiveData()



    fun getListDataSerch(query: String): Flow<PagingData<movie>> {
        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { SearchPagingSource(query,apimanager.getwebbservices()) }).flow.cachedIn(
            viewModelScope
        )
    }
}


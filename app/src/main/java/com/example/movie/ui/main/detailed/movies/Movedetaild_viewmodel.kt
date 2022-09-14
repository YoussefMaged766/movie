package com.example.movie.ui.main.detailed


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie.models.*
import com.example.movie.ui.main.detailed.movies.RecommendedPagingSource
import com.example.movie.util.apimanager
import kotlinx.coroutines.flow.Flow


class movedetaild_viewmodel :ViewModel() {

    fun getListDataRecommended(id: Int): Flow<PagingData<movie>> {
        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { RecommendedPagingSource(id,apimanager.getwebbservices()) }).flow.cachedIn(
            viewModelScope
        )
    }

}
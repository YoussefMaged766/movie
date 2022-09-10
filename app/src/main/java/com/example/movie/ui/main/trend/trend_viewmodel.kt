package com.example.movie.ui.main.trend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie.models.*
import com.example.movie.util.apimanager
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class trend_viewmodel : ViewModel() {

    var errormassage: MutableLiveData<String> = MutableLiveData()
    var response_tv_trailer: MutableLiveData<String> = MutableLiveData()

    lateinit var movies: MutableLiveData<List<movie>>


    init {
        getListDataTrendMovie()
        getListDataTrendTV()

    }


    fun gettrailer_tv(id: Int?) {

        apimanager.getwebbservices()
            .get_trailer_tv(id)
            .enqueue(object : Callback<TrailerResponse> {
                override fun onResponse(
                    call: Call<TrailerResponse>,
                    response: Response<TrailerResponse>
                ) {
                    if (response.isSuccessful) {

                        response_tv_trailer.value = response.body()?.results?.get(0)?.key


                    } else {
                        errormassage.value = response.message()
                    }
                }

                override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                    errormassage.value = t.localizedMessage
                }
            })

    }




    fun getListDataTrendMovie(): Flow<PagingData<movie>> {

        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { MovieTrendPagingSource(apimanager.getwebbservices()) }).flow.cachedIn(
            viewModelScope
        )


    }
    fun getListDataTrendTV(): Flow<PagingData<ResultsItem_trendTV>> {

        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { TVTrendPagingSource(apimanager.getwebbservices()) }).flow.cachedIn(
            viewModelScope
        )


    }

// naming convention


}
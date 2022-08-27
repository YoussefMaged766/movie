package com.example.movie.ui.main.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn


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


    var response_toprated: MutableLiveData<List<movie?>> = MutableLiveData()
    var response_upcoming: MutableLiveData<List<movie?>> = MutableLiveData()
    var response_popular: MutableLiveData<List<movie?>> = MutableLiveData()
    var pages_toprated: MutableLiveData<Int> = MutableLiveData()
    var pages_upcoming: MutableLiveData<Int> = MutableLiveData()
    var pages_popular: MutableLiveData<Int> = MutableLiveData()
    var errormassage: MutableLiveData<String> = MutableLiveData()








    fun getdatafromapi_toprated(page: Int) {

        apimanager.getwebbservices()
            .getTopRatedmovies(constants.api_key, "en-US", page)
            .enqueue(object : Callback<TopRatedResponse> {
                override fun onResponse(
                    call: Call<TopRatedResponse>,
                    response: Response<TopRatedResponse>
                ) {
                    if (response.isSuccessful) {
                        response_toprated.value = response.body()?.results
                        pages_toprated.value = response.body()?.totalPages


                    } else {
                        errormassage.value = response.message()
                    }
                }

                override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                    errormassage.value = t.localizedMessage
                }
            })
    }

    fun getdatafromapi_upcoming(page: Int) {

        apimanager.getwebbservices()
            .getupcomingmovies( page)
            .enqueue(object : Callback<TopRatedResponse> {
                override fun onResponse(
                    call: Call<TopRatedResponse>,
                    response: Response<TopRatedResponse>
                ) {
                    if (response.isSuccessful) {
                        response_upcoming.value = response.body()?.results
                        pages_upcoming.value = response.body()?.totalPages


                    } else {
                        errormassage.value = response.message()
                    }
                }

                override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                    errormassage.value = t.localizedMessage
                }
            })
    }

    fun getdatafromapi_poppular(page: Int) {

        apimanager.getwebbservices()
            .getpopularmovies( page)
            .enqueue(object : Callback<TopRatedResponse> {
                override fun onResponse(
                    call: Call<TopRatedResponse>,
                    response: Response<TopRatedResponse>
                ) {
                    if (response.isSuccessful) {
                        response_popular.value = response.body()?.results
                        pages_popular.value = response.body()?.totalPages


                    } else {
                        errormassage.value = response.message()
                    }
                }

                override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                    errormassage.value = t.localizedMessage
                }
            })
    }

    fun get_data(): MutableLiveData<List<movie?>> {
        return response_toprated
    }

    fun getListData(): Flow<PagingData<movie>> {
        return Pager (config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = {MoviesPagingSource(apimanager.getwebbservices())}).flow.cachedIn(viewModelScope)
    }

}
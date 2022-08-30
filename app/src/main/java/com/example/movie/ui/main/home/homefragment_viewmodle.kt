package com.example.movie.ui.main.home


import android.os.Parcelable
import android.util.Log
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


     var response_toprated: MutableLiveData<List<movie>> = MutableLiveData()
     var response_upcoming: MutableLiveData<List<movie>> = MutableLiveData()
     var response_popular: MutableLiveData<List<movie>> = MutableLiveData()

    var errormassage: MutableLiveData<String> = MutableLiveData()




init {
    getPopularData()
    getTopRatedData()
//    getUpComingData()
}







    fun getdatafromapi_toprated() {


    }

    fun getdatafromapi_upcoming(page:Int) {
        apimanager.getwebbservices()
            .getupcomingmovies(page)
            .enqueue(object : Callback<TopRatedResponse> {
                override fun onResponse(
                    call: Call<TopRatedResponse>,
                    response: Response<TopRatedResponse>
                ) {
                    if (response.isSuccessful) {
                        response_upcoming.value = response.body()?.results


                    } else {
                        errormassage.value = response.message()
                    }
                }

                override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                    errormassage.value = t.localizedMessage
                }
            })

    }

    fun getdatafromapi_poppular() {


    }

//    fun get_data(): MutableLiveData<List<movie?>> {
//        return response_toprated
//    }

    fun getTopRatedData(){
        response_toprated = MoviesRepo.getTopRatedMovies()
    }
//    fun getUpComingData(){
//        response_upcoming = MoviesRepo.getUpComingMovies()
//    }
    fun  getPopularData(){
        response_popular = MoviesRepo.getPopularMovies()
        Log.e("getPopularData: ","hello" )
    }


    fun getListData(): Flow<PagingData<movie>> {
        return Pager (config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = {MoviesPagingSource(apimanager.getwebbservices())}).flow.cachedIn(viewModelScope)
    }

}
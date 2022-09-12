package com.example.movie.ui.main.detailed

import androidx.lifecycle.MutableLiveData
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class movedetaild_viewmodel :ViewModel() {

    var response_toprated:MutableLiveData<String?> = MutableLiveData()
    var errormassage :MutableLiveData<String> = MutableLiveData()
    var response_reccommended :MutableLiveData<List<movie?>> =  MutableLiveData()

fun gettrsiler_movie(id: Int?) {
    apimanager.getwebbservices()
        .get_trailer(id)
        .enqueue(object : Callback<TrailerResponse> {
            override fun onResponse(
                call: Call<TrailerResponse>,
                response: Response<TrailerResponse>
            ) {
                if (response.isSuccessful) {

                    response_toprated.value = response.body()?.results?.get(0)?.key



                } else {
                    errormassage.value= response.message()
                }
            }

            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                errormassage.value= t.localizedMessage
            }
        })
}
//    fun getrecommended_movie(id: Int?) {
//
//        apimanager.getwebbservices()
//            .get_recommended(id)
//            .enqueue(object : Callback<TopRatedResponse> {
//                override fun onResponse(
//                    call: Call<TopRatedResponse>,
//                    response: Response<TopRatedResponse>
//                ) {
//                    if (response.isSuccessful) {
//
//                        response_reccommended.value = response.body()?.results
//
//
//
//                    } else {
//                        errormassage.value= response.message()
//                    }
//                }
//
//                override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
//                    errormassage.value= t.localizedMessage
//                }
//            })
//    }

    fun getListDataRecommended(id: Int): Flow<PagingData<movie>> {
        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { RecommendedPagingSource(id,apimanager.getwebbservices()) }).flow.cachedIn(
            viewModelScope
        )
    }

}
package com.example.movie.ui.main.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.models.TopRatedResponse
import com.example.movie.models.movie
import com.example.movie.util.apimanager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class viewmodel : ViewModel() {
    var response_search: MutableLiveData<List<movie?>> = MutableLiveData()
    var error : MutableLiveData<String> = MutableLiveData()

    fun getsearched_movies(query: String) {
        apimanager.getwebbservices().getserchmovies(query)
            .enqueue(object : Callback<TopRatedResponse> {
                override fun onResponse(
                    call: Call<TopRatedResponse>,
                    response: Response<TopRatedResponse>
                ) {
                    if (response.isSuccessful) {
                        response_search.value = response.body()?.results
                    } else error.value =response.message()
                }

                override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                  error.value= t.localizedMessage
                }

            })
    }
}


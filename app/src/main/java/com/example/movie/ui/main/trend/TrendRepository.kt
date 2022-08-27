package com.example.movie.ui.main.trend

import androidx.lifecycle.MutableLiveData
import com.example.movie.models.TopRatedResponse
import com.example.movie.models.movie
import com.example.movie.util.apimanager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class TrendRepository {
    companion object{
        val movie_response = MutableLiveData<List<movie>>()

        fun  getmovies(): MutableLiveData<List<movie>> {
            apimanager.getwebbservices().get_trend_movie().enqueue(object :Callback<TopRatedResponse>{
                override fun onResponse(
                    call: Call<TopRatedResponse>,
                    response: Response<TopRatedResponse>
                ) {
                    movie_response.value = response.body()?.results
                }

                override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
            return movie_response
        }
    }

}
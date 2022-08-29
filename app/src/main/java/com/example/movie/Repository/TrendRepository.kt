package com.example.movie.Repository

import androidx.lifecycle.MutableLiveData
import com.example.movie.models.*
import com.example.movie.util.apimanager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class TrendRepository {
    companion object{
        val movie_response:MutableLiveData<List<movie>> = MutableLiveData<List<movie>>()
        val tv_response :MutableLiveData<List<ResultsItem_trendTV?>> = MutableLiveData<List<ResultsItem_trendTV?>>()
        var response_tv_trailer: MutableLiveData<String> = MutableLiveData()

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

        fun getTV() : MutableLiveData<List<ResultsItem_trendTV?>> {
            apimanager.getwebbservices().get_trend_tv().enqueue(object :Callback<TrendtvResponse>{
                override fun onResponse(
                    call: Call<TrendtvResponse>,
                    response: Response<TrendtvResponse>
                ) {
                 if (response.isSuccessful){
                     tv_response.value = response.body()?.results
                 }
                }

                override fun onFailure(call: Call<TrendtvResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
            return tv_response
        }

        fun getTvTrailer(id:Int):MutableLiveData<String>{
            apimanager.getwebbservices().get_trailer_tv(id).enqueue(object :Callback<TrailerResponse>{
                override fun onResponse(
                    call: Call<TrailerResponse>,
                    response: Response<TrailerResponse>
                ) {
                    response_tv_trailer.value = response.body()?.results?.get(0)?.key
                }

                override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
            return response_tv_trailer
        }

    }

}
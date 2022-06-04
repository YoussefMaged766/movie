package com.example.movie.ui.main.trend

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.models.*
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class trend_viewmodel:ViewModel() {
    var response_trend:MutableLiveData<List<ResultsItem_trend?>> = MutableLiveData()
    var response_trend_tv : MutableLiveData<List<ResultsItem_trendTV?>> = MutableLiveData()
    var errormassage:MutableLiveData<String> = MutableLiveData()
    var response_tv_trailer:MutableLiveData<String> = MutableLiveData()
    fun gettrend_movie() {

        apimanager.getwebbservices()
            .get_trend_movie( constants.api_key, "en-US")
            .enqueue(object : Callback<TrendResponse> {
                override fun onResponse(
                    call: Call<TrendResponse>,
                    response: Response<TrendResponse>
                ) {
                    if (response.isSuccessful) {

                        response_trend.value = response.body()?.results
                        Log.e( "onResponse: ", response.body()?.results.toString())


                    } else {
                        errormassage.value= response.message()
                    }
                }

                override fun onFailure(call: Call<TrendResponse>, t: Throwable) {
                    errormassage.value= t.localizedMessage
                }
            })
    }

    fun gettrend_tv() {

        apimanager.getwebbservices()
            .get_trend_tv( constants.api_key, "en-US")
            .enqueue(object : Callback<TrendtvResponse> {
                override fun onResponse(
                    call: Call<TrendtvResponse>,
                    response: Response<TrendtvResponse>
                ) {
                    if (response.isSuccessful) {

                        response_trend_tv.value = response.body()?.results
                        Log.e( "onResponse: ", response.body()?.results.toString())


                    } else {
                        errormassage.value= response.message()
                    }
                }

                override fun onFailure(call: Call<TrendtvResponse>, t: Throwable) {
                    errormassage.value= t.localizedMessage
                }
            })
    }

    fun gettrailer_tv(id: Int?) {

        apimanager.getwebbservices()
            .get_trailer_tv(id, constants.api_key, "en-US")
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
}
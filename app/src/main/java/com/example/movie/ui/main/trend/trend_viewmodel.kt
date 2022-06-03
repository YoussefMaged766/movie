package com.example.movie.ui.main.trend

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.models.ResultsItem_trend
import com.example.movie.models.TrendResponse
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class trend_viewmodel:ViewModel() {
    var response_trend:MutableLiveData<List<ResultsItem_trend?>> = MutableLiveData()
    var errormassage:MutableLiveData<String> = MutableLiveData()
    fun gettrend(type: String?) {

        apimanager.getwebbservices()
            .get_trend(type, constants.api_key, "en-US")
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
}
package com.example.movie.ui.main


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.models.TopRatedResponse
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import com.example.movie.models.movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class movieviewmodle : ViewModel() {


    var response1: MutableLiveData<List<movie?>> = MutableLiveData()
    var errormassage :MutableLiveData<String> = MutableLiveData()



    fun getdatafromapi( page: Int) {

        apimanager.getwebbservices()
            .getTopRatedmovies(constants.api_key, "en-US", page)
            .enqueue(object : Callback<TopRatedResponse> {
                override fun onResponse(
                    call: Call<TopRatedResponse>,
                    response: Response<TopRatedResponse>
                ) {
                    if (response.isSuccessful) {
                        response1.value = response.body()?.results as List<movie>

                    } else {
                        errormassage.value= response.message()
                    }
                }

                override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                    errormassage.value= t.localizedMessage
                }
            })
    }
}
package com.example.movie.ui.main.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.models.TopRatedResponse
import com.example.movie.models.TrailerResponse
import com.example.movie.models.movie
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class viewmodel : ViewModel() {

    var response_category: MutableLiveData<List<movie?>> = MutableLiveData()
    var errormassage :MutableLiveData<String> = MutableLiveData()
    fun getmovies_by_category(id: Int) {

        apimanager.getwebbservices()
            .getmovies_by_category(constants.api_key, "en-US", id)
            .enqueue(object : Callback<TopRatedResponse> {
                override fun onResponse(
                    call: Call<TopRatedResponse>,
                    response: Response<TopRatedResponse>
                ) {
                    if (response.isSuccessful) {

                        response_category.value = response.body()?.results


                    } else {
                        errormassage.value = response.message()
                    }
                }

                override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                    errormassage.value = t.localizedMessage
                }
            })
    }
}
package com.example.movie.ui.main.detailed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.models.ResultsItem
import com.example.movie.models.TrailerResponse
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class movedetaild_viewmodel :ViewModel() {

var response_toprated:MutableLiveData<String> = MutableLiveData()
    var errormassage :MutableLiveData<String> = MutableLiveData()

fun gettrsiler_movie(id: Int?) {

    apimanager.getwebbservices()
        .get_trailer(id,constants.api_key, "en-US")
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

}
package com.example.movie.ui.main.detailed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.models.*
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class movedetaild_viewmodel :ViewModel() {

    var response_toprated:MutableLiveData<String> = MutableLiveData()
    var errormassage :MutableLiveData<String> = MutableLiveData()
    var response_reccommended :MutableLiveData<List<ResultsItem1?>> =  MutableLiveData()

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
    fun getrecommended_movie(id: Int?) {

        apimanager.getwebbservices()
            .get_recommended(id,constants.api_key, "en-US")
            .enqueue(object : Callback<RecommendedResponse> {
                override fun onResponse(
                    call: Call<RecommendedResponse>,
                    response: Response<RecommendedResponse>
                ) {
                    if (response.isSuccessful) {

                        response_reccommended.value = response.body()?.results



                    } else {
                        errormassage.value= response.message()
                    }
                }

                override fun onFailure(call: Call<RecommendedResponse>, t: Throwable) {
                    errormassage.value= t.localizedMessage
                }
            })
    }

}
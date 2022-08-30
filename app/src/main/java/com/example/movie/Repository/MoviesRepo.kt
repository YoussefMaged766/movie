package com.example.movie.Repository

import androidx.lifecycle.MutableLiveData
import com.example.movie.models.TopRatedResponse
import com.example.movie.models.movie
import com.example.movie.util.apimanager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepo {
    companion object {
        var response_toprated: MutableLiveData<List<movie>> = MutableLiveData()
        var response_upcoming: MutableLiveData<List<movie>> = MutableLiveData()
        var response_popular: MutableLiveData<List<movie>> = MutableLiveData()
        var errormassage: MutableLiveData<String> = MutableLiveData()

        fun getTopRatedMovies(): MutableLiveData<List<movie>> {
            apimanager.getwebbservices()
                .getTopRatedmovies()
                .enqueue(object : Callback<TopRatedResponse> {
                    override fun onResponse(
                        call: Call<TopRatedResponse>,
                        response: Response<TopRatedResponse>
                    ) {
                        if (response.isSuccessful) {
                            response_toprated.value = response.body()?.results


                        } else {
                            errormassage.value = response.message()
                        }
                    }

                    override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                        errormassage.value = t.localizedMessage
                    }
                })
            return response_toprated
        }

        fun getPopularMovies(): MutableLiveData<List<movie>> {
            apimanager.getwebbservices()
                .getpopularmovies()
                .enqueue(object : Callback<TopRatedResponse> {
                    override fun onResponse(
                        call: Call<TopRatedResponse>,
                        response: Response<TopRatedResponse>
                    ) {
                        if (response.isSuccessful) {
                            response_popular.value = response.body()?.results


                        } else {
                            errormassage.value = response.message()
                        }
                    }

                    override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                        errormassage.value = t.localizedMessage
                    }
                })
            return response_popular
        }

        fun getUpComingMovies(): MutableLiveData<List<movie>> {

            return response_upcoming

        }
    }

}
package com.example.movie.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movie.models.TopRatedResponse
import com.example.movie.models.movie
import com.example.movie.util.apimanager
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepo {
    companion object {

        var response_upcoming: MutableLiveData<List<movie>> = MutableLiveData()
        var response_popular: MutableLiveData<List<movie>> = MutableLiveData()
        var errormassage: MutableLiveData<String> = MutableLiveData()





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
            apimanager.getwebbservices()
                .getupcomingmovies()
                .enqueue(object : Callback<TopRatedResponse> {
                    override fun onResponse(
                        call: Call<TopRatedResponse>,
                        response: Response<TopRatedResponse>
                    ) {
                        if (response.isSuccessful) {
                            response_upcoming.value = response.body()?.results


                        } else {
                            errormassage.value = response.message()
                        }
                    }

                    override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                        errormassage.value = t.localizedMessage
                    }
                })

            return response_upcoming

        }

//        fun getTopRatedMovies(): LiveData<List<movie>> {
//            val data = MutableLiveData<List<movie>>()
//            val job = Job()
//            GlobalScope.launch(IO + job) {
//                val response = apimanager.getwebbservices().getTopRatedmovies()
//                withContext(Main + SupervisorJob(job)) {
//                    data.value = response.results
//                }
//                job.complete()
//                job.cancel()
//            }
//            return data
//        }




    }


}
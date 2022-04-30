package com.example.movie.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class apimanager {
    companion object{
        private var retrofit: Retrofit? = null
        @Synchronized
        private fun  getinstance(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                return retrofit!!
            }
            return retrofit!!
        }

        fun getwebbservices(): webservices {
            return getinstance().create(webservices::class.java)

        }
    }

}
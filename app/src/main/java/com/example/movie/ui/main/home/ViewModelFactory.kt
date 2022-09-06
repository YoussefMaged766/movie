package com.example.movie.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie.Repository.MoviesRepo
import com.example.movie.util.apimanager
import com.example.movie.util.webservices

class ViewModelFactory(private val webservices: webservices ):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(webservices)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
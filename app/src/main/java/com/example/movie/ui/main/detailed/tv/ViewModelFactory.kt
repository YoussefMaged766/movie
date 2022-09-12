package com.example.movie.ui.main.detailed.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie.Repository.MainRepository
import com.example.movie.util.WebServices

 class ViewModelFactoryTV(private val webservices: WebServices):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TVViewModel::class.java)) {
            return TVViewModel(MainRepository(webservices)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
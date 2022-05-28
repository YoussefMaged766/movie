package com.example.movie.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.movie.models.movie


class Database_viewmodel(application: Application) : AndroidViewModel(application) {
     var repository: Repository = Repository(application)


    fun insertMovie(movie: movie?) {
        repository.insertMovie(movie)
    }

    fun getAllFavoriteMovies(): LiveData<List<movie?>?>? {
        return repository.getAllFavoriteMovies()
    }


    fun setFavoriteMovie(movieId: Int?) {
        if (movieId != null) {
            repository.setFavoriteMovie(movieId)
        }
    }


    fun UnFavoriteMovie(movieId: Int?) {
        if (movieId != null) {
            repository.UnFavoriteMovie(movieId)
        }
    }

    fun IsFavorite(movieId: Int): LiveData<Int?>? {
        return repository.IsFavorite(movieId)
    }

    fun deleteMovie(movie: movie?) {
        repository.deleteMovie(movie)
    }

}
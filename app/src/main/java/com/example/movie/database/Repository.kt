package com.example.movie.database

import android.app.Application
import android.graphics.Movie
import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.movie.models.movie


class Repository(application: Application) {
    lateinit var moviedao: moviedao

    init {
        val db = MovieRoomDatabase.getINSTANCE(application)
        moviedao = db?.movieDAO()!!
    }
    fun insertMovie(movie: movie?) {
        MovieRoomDatabase.EXECUTOR_SERVICE.execute(Runnable { moviedao.insertMovie(movie) })
    }

    fun deleteMovie(movie: movie?) {
        MovieRoomDatabase.EXECUTOR_SERVICE.execute(Runnable { moviedao.deleteMovie(movie) })
    }

    fun getAllFavoriteMovies(): LiveData<List<movie?>?>? {
        return moviedao.getAllFavoriteMovies()
    }


    fun setFavoriteMovie(movieId: Int) {
        MovieRoomDatabase.EXECUTOR_SERVICE.execute(Runnable { moviedao.setFavoriteMovie(movieId) })
    }


    fun UnFavoriteMovie(movieId: Int) {
        MovieRoomDatabase.EXECUTOR_SERVICE.execute(Runnable { moviedao.UnFavoriteMovie(movieId) })
    }

    fun IsFavorite(movieId: Int): LiveData<Int?>? {
        return moviedao.IsFavorite(movieId)
    }


}
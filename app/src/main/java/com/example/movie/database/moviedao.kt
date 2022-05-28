package com.example.movie.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.movie.models.movie

@Dao
interface moviedao {
    @Insert
    fun insertMovie(movie: movie?)

    @Delete
    fun deleteMovie(movie: movie?)

    @Query("SELECT * From Movie where is_favorite = 1")
    fun getAllFavoriteMovies(): LiveData<List<movie?>?>?

    @Query("UPDATE Movie SET is_favorite = 1 WHERE id = :movieId")
    fun setFavoriteMovie(movieId: Int)

    @Query("UPDATE Movie SET is_favorite = 0 WHERE id =:movieId")
    fun UnFavoriteMovie(movieId: Int)

    @Query("SELECT is_favorite From movie WHERE id =:movieId")
    fun IsFavorite(movieId: Int): LiveData<Int?>?
}
package com.example.movie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movie.models.movie
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Database(entities = arrayOf(movie::class) ,version = 1,exportSchema = false)
abstract class  MovieRoomDatabase: RoomDatabase() {


    abstract fun movieDAO(): moviedao?




    companion object{
        private val NAME_DB = "Movie_Database"
        @Volatile
        var INSTANCE1: MovieRoomDatabase? = null
        private val NUMBER_THREADS = 4
        val EXECUTOR_SERVICE: ExecutorService = Executors.newFixedThreadPool(NUMBER_THREADS)
        fun getINSTANCE(context: Context): MovieRoomDatabase? {
            if (INSTANCE1 == null) {
                synchronized(MovieRoomDatabase::class.java) {
                    if (INSTANCE1 == null) {
                        INSTANCE1 = Room.databaseBuilder(
                            context.getApplicationContext(), MovieRoomDatabase::class.java, NAME_DB
                        ).build()
                    }
                }
            }
            return INSTANCE1
        }
    }


}
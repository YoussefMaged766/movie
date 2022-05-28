package com.example.movie.models

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movie")
data class movie(

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    @Expose
    val overview: String?,


    @field:SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null,


    @field:SerializedName("original_title")
    @Expose
    val originalTitle: String? = null,


    @field:SerializedName("video")
    val video: Boolean? = null,

    @ColumnInfo(name = "title")
    @field:SerializedName("title")

    val title: String? = null,




    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,

    @ColumnInfo(name = "backdrop_path")
    @field:SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null,

    @field:SerializedName("release_date")
    @Expose
    val releaseDate: String? = null,

    @field:SerializedName("popularity")
    @Expose
    val popularity: Double? = null,

    @ColumnInfo(name = "vote_average")
    @field:SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null,

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    @Expose
    val id: Int? = null,


    @field:SerializedName("adult")
    @Expose
    val adult: Boolean? = null,

    @field:SerializedName("vote_count")
    @Expose
    val voteCount: Int? = null,

    @ColumnInfo(name = "is_favorite")
    val IsFavorite: Int = 0

) : Serializable {
    @Ignore
    @field:SerializedName("genre_ids")
    val genreIds: List<Int?>?=null

//    constructor() : this(
//        "",
//        "",
//        "",
//        false,
//        "",
//        "",
//        "",
//        "",
//        0.0,
//        0.0,
//       0,
//        false,
//        0,
//        0
//    )

}






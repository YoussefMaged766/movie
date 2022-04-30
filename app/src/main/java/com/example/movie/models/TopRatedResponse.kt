package com.example.movie.models

import com.example.movie.models.movie
import com.google.gson.annotations.SerializedName

data class TopRatedResponse(

    @field:SerializedName("page")
	val page: Int? = null,

    @field:SerializedName("total_pages")
	val totalPages: Int? = null,

    @field:SerializedName("results")
	val results: List<movie?>? = null,

    @field:SerializedName("total_results")
	val totalResults: Int? = null
)
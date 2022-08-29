package com.example.movie.models

import com.example.movie.models.movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TopRatedResponse(

    @field:SerializedName("page")
	val page: Int? = null,

    @field:SerializedName("total_pages")
	val totalPages: Int? = null,

    @field:SerializedName("results")
	val results: List<movie>,

    @field:SerializedName("total_results")
	val totalResults: Int? = null,

    @field:SerializedName("status_message")
    val statusMessage: String? = null,

    @field:SerializedName("status_code")
    val statusCode: Int? = null,

    @field:SerializedName("success")
    val success: Boolean? = null
    )








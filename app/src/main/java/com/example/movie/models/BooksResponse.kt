package com.example.movie.models

import com.google.gson.annotations.SerializedName

data class BooksResponse(

	@field:SerializedName("result")
	val result: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("doc")
	val doc: List<DocItem?>? = null,

	@field:SerializedName("categories")
	val categories: List<String?>? = null
)

data class DocItem(

	@field:SerializedName("longDescription")
	val longDescription: String? = null,

	@field:SerializedName("discountRate")
	val discountRate: Int? = null,

	@field:SerializedName("pageCount")
	val pageCount: Int? = null,

	@field:SerializedName("isbn")
	val isbn: String? = null,

	@field:SerializedName("relatedBooks")
	val relatedBooks: List<String?>? = null,

	@field:SerializedName("shortDescription")
	val shortDescription: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("ratingsQuantity")
	val ratingsQuantity: Int? = null,

	@field:SerializedName("ratingsAverage")
	val ratingsAverage: Int? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("publishedDate")
	val publishedDate: String? = null,

	@field:SerializedName("categories")
	val categories: List<String?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("thumbnailUrl")
	val thumbnailUrl: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("authors")
	val authors: List<String?>? = null
)

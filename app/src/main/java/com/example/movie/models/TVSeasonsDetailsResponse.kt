package com.example.movie.models

import com.google.gson.annotations.SerializedName

data class TVSeasonsDetailsResponse(

	@field:SerializedName("air_date")
	val airDate: String? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("season_number")
	val seasonNumber: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("id")
	val id1: Int? = null,

	@field:SerializedName("episodes")
	val episodes: List<EpisodesItem?>? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null
)

data class GuestStarsItem(

	@field:SerializedName("character")
	val character: String? = null,

	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("credit_id")
	val creditId: String? = null,

	@field:SerializedName("known_for_department")
	val knownForDepartment: String? = null,

	@field:SerializedName("original_name")
	val originalName: String? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("profile_path")
	val profilePath: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@field:SerializedName("order")
	val order: Int? = null
)

data class CrewItem(

	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("credit_id")
	val creditId: String? = null,

	@field:SerializedName("known_for_department")
	val knownForDepartment: String? = null,

	@field:SerializedName("original_name")
	val originalName: String? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("profile_path")
	val profilePath: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("job")
	val job: String? = null,

	@field:SerializedName("department")
	val department: String? = null,

	@field:SerializedName("adult")
	val adult: Boolean? = null
)

data class EpisodesItem(

	@field:SerializedName("production_code")
	val productionCode: String? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("show_id")
	val showId: Int? = null,

	@field:SerializedName("season_number")
	val seasonNumber: Int? = null,

	@field:SerializedName("runtime")
	val runtime: Int? = null,

	@field:SerializedName("still_path")
	val stillPath: String? = null,

	@field:SerializedName("crew")
	val crew: List<CrewItem?>? = null,

	@field:SerializedName("guest_stars")
	val guestStars: List<GuestStarsItem?>? = null,

	@field:SerializedName("air_date")
	val airDate: String? = null,

	@field:SerializedName("episode_number")
	val episodeNumber: Int? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null
)

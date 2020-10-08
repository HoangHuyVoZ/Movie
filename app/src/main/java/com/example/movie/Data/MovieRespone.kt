package com.example.movie.Data


import com.google.gson.annotations.SerializedName


    data class MovieRespone(
val results: List<Result>,
        @SerializedName("page")
val page: Int,
@SerializedName("total_results")
val totalresult: Int,
@SerializedName("total_pages")
val totalpages: Int,
)

data class Result(
    val id: Int,    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,

)
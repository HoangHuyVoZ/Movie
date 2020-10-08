package com.example.movie.Data


import com.google.gson.annotations.SerializedName


data class TrailerRespone(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Trailer>
)
data class Trailer(

    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("site")
    val site: String,


    )
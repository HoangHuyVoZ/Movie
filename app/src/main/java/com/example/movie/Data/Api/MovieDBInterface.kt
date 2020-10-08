package com.example.movie.Data.Api

import com.example.movie.Data.MovieDetail
import com.example.movie.Data.MovieRespone
import com.example.movie.Data.TrailerRespone
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBInterface {
    //https://api.themoviedb.org/3/movie/9648?api_key=c5f75fa31358e57ee8b4f9b893667450
    //https://api.themoviedb.org/3/movie/popular?api_key=c5f75fa31358e57ee8b4f9b893667450
    //https://api.themoviedb.org/3
    @GET("movie/{id}")
    fun getMoviesDetail(@Path("id")id: Int,
                         @Query("api_key")key: String): Call<MovieDetail>
    @GET("movie/{category}")
    fun getMovies(@Path("category")category: String,
                  @Query("api_key") key: String,
                  @Query("page") page : Int,
                  @Query("language") language: String): Call<MovieRespone>
    @GET("movie/{id}/videos")
    fun getTrailer(@Path("id")id: Int,
                    @Query("api_key")key :String
                   ): Call<TrailerRespone>

}
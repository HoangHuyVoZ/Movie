package com.example.movie.UI.Detail

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movie.Data.*
import com.example.movie.Data.Api.API
import com.example.movie.Data.Api.MovieDBInterface
import com.example.movie.Data.MovieDetail
import com.example.movie.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_video_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetail : YouTubeBaseActivity() {
    lateinit var adapter: TrailerAdapter
    lateinit var layoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        val bundle :Bundle ?=intent.extras
        val id = bundle!!.getInt("id")
        val star = bundle!!.getInt("vote")
        layoutManager=LinearLayoutManager(this)
        setupRecyclerView()
        val request = API.buildService(MovieDBInterface::class.java)
        val call = request.getMoviesDetail(id,getString(R.string.api_key))
        call.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if (response.isSuccessful) {
                    rating.rating = response.body()!!.voteAverage.toFloat()
                    movie_populary.text = response.body()!!.popularity.toString()
                    movie_title.text= response.body()!!.title
                    movie_overview.text=response.body()!!.overview

                }
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
               Toast.makeText(this@MovieDetail,"không tìm thấy !!",Toast.LENGTH_SHORT).show()
            }
        })
        val trailers = request.getTrailer(id,getString(R.string.api_key))
        trailers.enqueue(object : Callback<TrailerRespone> {
            override fun onFailure(call: Call<TrailerRespone>, t: Throwable) {
                Toast.makeText(this@MovieDetail, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<TrailerRespone>, response: Response<TrailerRespone>
            ) {
                var list: ArrayList<Trailer> = response.body()!!.results as ArrayList<Trailer>


                if (list != null) {
                    adapter.addList(list)
                } else {
                    tb.visibility = View.VISIBLE
                }
                video.initialize("AIzaSyDIWyZEo9vSBtEaXWbPXEK4Vu2gBLjRV3M",
                    object : YouTubePlayer.OnInitializedListener {
                        override fun onInitializationFailure(
                            p0: YouTubePlayer.Provider?,
                            p1: YouTubeInitializationResult?
                        ) {
                            Toast.makeText(
                                this@MovieDetail,
                                "không tìm thấy !!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onInitializationSuccess(
                            p0: YouTubePlayer.Provider?,
                            p1: YouTubePlayer?,
                            p2: Boolean
                        ) {
                            var videoid = ""
                            for (item in list) {
                                videoid = item.key
                                break
                            }
                            p1!!.loadVideo(videoid)

                            if(star<=5){
                                p1!!.pause()
                            }

                        }
                    })


            }
        })
    }
    private fun setupRecyclerView() {
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager= layoutManager
        adapter= TrailerAdapter()
        recyclerview.adapter= adapter
    }


}


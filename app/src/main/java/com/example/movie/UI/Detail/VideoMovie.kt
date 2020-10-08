package com.example.movie.UI.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movie.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_video_movie.*

class VideoMovie : YouTubeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_movie)
        val bundle :Bundle ?=intent.extras
        val names = bundle!!.getString("name")
        val key = bundle!!.getString("key")
        name?.text = names
                trailer.initialize("AIzaSyDIWyZEo9vSBtEaXWbPXEK4Vu2gBLjRV3M",object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@VideoMovie,"không tìm thấy !!!",Toast.LENGTH_SHORT).show()
            }

            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1!!.loadVideo(key)



            }
        })
    }
}
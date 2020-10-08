package com.example.movie.Data

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.UI.Detail.MovieDetail
import com.example.movie.UI.Detail.VideoMovie
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class TrailerAdapter(): RecyclerView.Adapter<TrailerViewHolder>() {
    private var trailer = ArrayList<Trailer>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trailer, parent, false)

        return TrailerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trailer.size
    }


    fun addList(items: ArrayList<Trailer>){
        trailer.addAll(items)
        notifyDataSetChanged()
    }
    fun clear(){
        trailer.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        return holder.bind(trailer[position])

    }
}




class TrailerViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
//    private val youtube : YouTubePlayerView =itemView.findViewById(R.id.trailer)
    private val title : TextView = itemView.findViewById(R.id.title)
    fun bind(trailer: Trailer) {

        title.text = "-- ${trailer.name}"
        val name= trailer.name
        val key = trailer.key
        title.setOnClickListener {
            val intent = Intent(itemView.context, VideoMovie::class.java)
            intent.putExtra("name", name )
            intent.putExtra("key", key )
            itemView.context.startActivity(intent)
        }
    }


}
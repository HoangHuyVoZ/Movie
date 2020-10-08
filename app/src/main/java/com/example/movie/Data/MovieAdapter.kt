package com.example.movie.Data

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.UI.Detail.MovieDetail

class MoviesAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_ONE = 5

    }
    private val context: Context = context
    private var movies = ArrayList<Result>()
    private inner class View1ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var title: TextView = itemView.findViewById(R.id.title)
        var des: TextView = itemView.findViewById(R.id.Description)
        var card: CardView = itemView.findViewById(R.id.card)
        fun bind(position: Int) {
            val movie = movies[position]
            title.text= movie.title
            des.text=movie.overview
            val orientation = itemView.context.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w300/${movie.poster_path}").into(image)
            // ...
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500/${movie.poster_path}").into(image)
            // ...
        }
            val id: Int =movie.id
            card.setOnClickListener {

              val intent = Intent(itemView.context, MovieDetail::class.java)
            intent.putExtra("id", id )

            itemView.context.startActivity(intent)
        }
        }
    }

    private inner class View2ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var card: CardView = itemView.findViewById(R.id.card)
        var title: TextView? = itemView.findViewById(R.id.title)
        var des: TextView? =itemView.findViewById(R.id.Description)
        fun bind(position: Int) {
            val movie = movies[position]
            val orientation = itemView.context.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w300/${movie.backdrop_path}").into(image)
                // ...
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500/${movie.backdrop_path}").into(image)
                title?.text = movie.title
                des?.text = movie.overview
                // ...
            }
            val id: Int =movie.id
            card.setOnClickListener {

                val intent = Intent(itemView.context, MovieDetail::class.java)
                intent.putExtra("id", id )
                intent.putExtra("vote",movie.voteAverage)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
//
//        return MoviesViewHolder(view)
        if (viewType <= VIEW_TYPE_ONE) {
            return View1ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
            )
        }
        return View2ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movie_5star, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (movies[position].voteAverage.toInt() <= VIEW_TYPE_ONE) {
            (holder as View1ViewHolder).bind(position)
        } else {
            (holder as View2ViewHolder).bind(position)
        }

    }
    override fun getItemViewType(position: Int): Int {
        return movies[position].voteAverage.toInt()
    }
    fun addList(items: MutableList<Result>){
        movies.addAll(items)
        notifyDataSetChanged()
    }
    fun clear(){
        movies.clear()
        notifyDataSetChanged()
    }
}

//class MoviesViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
//    private val photo:ImageView = itemView.findViewById(R.id.image)
//    private val title:TextView = itemView.findViewById(R.id.title)
//    private val overview:TextView = itemView.findViewById(R.id.Description)
//    private val card:CardView= itemView.findViewById(R.id.card)
//    fun bind(movie: Result) {
//        val orientation = itemView.context.resources.configuration.orientation
//        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w300/${movie.poster_path}").into(photo)
//            // ...
//        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500/${movie.backdrop_path}").into(photo)
//            // ...
//        }
//        title.text = movie.title
//        overview.text = movie.overview
//        val id: Int =movie.id
//        card.setOnClickListener {
//
//            val intent = Intent(itemView.context, MovieDetail::class.java)
//            intent.putExtra("id", id )
//
//            itemView.context.startActivity(intent)
//        }
//    }



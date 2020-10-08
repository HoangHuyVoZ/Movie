package com.example.movie.UI

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.Data.Api.API
import com.example.movie.Data.Api.MovieDBInterface
import com.example.movie.Data.MovieRespone
import com.example.movie.Data.MoviesAdapter
import com.example.movie.Data.Result
import com.example.movie.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_video_movie.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity(){
    var isloading= false
    var page =1
    var totalpage =1
    lateinit var movies : ArrayList<Result>
    lateinit var adapter: MoviesAdapter
    lateinit var layoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            layoutManager=LinearLayoutManager(this)
            setupRecyclerView()
            getData(false)
            addScrolListener()
            swipeLayout.setOnRefreshListener {
                adapter.clear()
                page=1
                getData(true)
                Toast.makeText(this,"Reloading successfully !",Toast.LENGTH_SHORT).show()
        }



    }

    private fun getData(isOnRefresh: Boolean) {
        isloading= true
         if(!isOnRefresh) progressBar.visibility = View.VISIBLE
        Handler().postDelayed({

            val request = API.buildService(MovieDBInterface::class.java)
            val call = request.getMovies("popular",getString(R.string.api_key), page, Locale.getDefault().language)
            call.enqueue(object : Callback<MovieRespone>{
                override fun onResponse(call: Call<MovieRespone>, response: Response<MovieRespone>) {
                    totalpage = response.body()!!.totalresult
                    val list = response.body()!!.results
                    if(list!=null){
                        adapter.addList(list as MutableList<Result>)
                    }
                    progressBar.visibility= View.INVISIBLE
                    isloading=false
                    swipeLayout.isRefreshing = false

                }


                override fun onFailure(call: Call<MovieRespone>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }

            })
        },2000)

    }

    private fun setupRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= layoutManager
        adapter=MoviesAdapter(this)
        recyclerView.adapter= adapter
    }

    fun addScrolListener(){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val pastVisibleItem =layoutManager.findLastVisibleItemPosition()

                val total= adapter.itemCount
                Log.d("a", (pastVisibleItem).toString())
                if(!isloading && pastVisibleItem==total-1){

                        page++
                        getData(false)

                }
                super.onScrolled(recyclerView, dx, dy)
            }

        })
    }
    }

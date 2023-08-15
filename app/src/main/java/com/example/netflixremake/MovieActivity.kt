package com.example.netflixremake

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.model.Movie
import com.example.netflixremake.model.MovieDetail
import com.example.netflixremake.util.DownloadImageTask
import com.example.netflixremake.util.MovieTask

class MovieActivity : AppCompatActivity(), MovieTask.Callback {
    private lateinit var textTitle: TextView
    private lateinit var textDesc: TextView
    private lateinit var textCast: TextView
    private lateinit var adapter: MovieAdapter
    private lateinit var progress: ProgressBar

    private val movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        textTitle = findViewById(R.id.text_movie_title)
        textDesc = findViewById(R.id.text_movie_desc)
        textCast = findViewById(R.id.text_movie_cast)
        progress = findViewById(R.id.progress_movie)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_movie_similar)

        val id = intent?.getIntExtra("id", 0) ?: throw IllegalStateException("ID não foi encontrado!")

        val url = "https://api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=f95c1bcb-03c1-48d3-b461-e38402a7ecb5"

        MovieTask(this).execute(url)

        textTitle.text = "Batman Beggins"
        textDesc.text = "Essa é a descrição do filme do batman"
        textCast.text = getString(R.string.cast, "Ator A, Ator B, Atriz A, Atriz B")

        adapter = MovieAdapter(movies, R.layout.movie_item_similar)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter

        val toolbar: Toolbar = findViewById(R.id.movie_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPreExecute() {
        progress.visibility = View.VISIBLE
    }

    override fun onResult(movieDetail: MovieDetail) {
        progress.visibility = View.GONE

        textTitle.text = movieDetail.movie.title
        textDesc.text = movieDetail.movie.desc
        textCast.text = getString(R.string.cast, movieDetail.movie.cast)

        movies.clear()
        movies.addAll(movieDetail.similars)
        adapter.notifyDataSetChanged()

        DownloadImageTask(object : DownloadImageTask.Callback {
            override fun onResult(bitmap: Bitmap) {
                val layerDrawable: LayerDrawable =
                    ContextCompat.getDrawable(this@MovieActivity, R.drawable.shadows) as LayerDrawable
                val movieCover = BitmapDrawable(resources, bitmap)

                layerDrawable.setDrawableByLayerId(R.id.cover_drawable, movieCover)

                val coverImg: ImageView = findViewById(R.id.movie_img)
                coverImg.setImageDrawable(layerDrawable)
            }
        }).execute(movieDetail.movie.coverUrl)
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
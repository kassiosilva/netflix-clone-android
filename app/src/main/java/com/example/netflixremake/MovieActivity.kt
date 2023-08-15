package com.example.netflixremake

import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.model.Movie

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val textTitle: TextView = findViewById(R.id.text_movie_title)
        val textDesc: TextView = findViewById(R.id.text_movie_desc)
        val textCast: TextView = findViewById(R.id.text_movie_cast)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_movie_similar)

        textTitle.text = "Batman Beggins"
        textDesc.text = "Essa é a descrição do filme do batman"
        textCast.text = getString(R.string.cast, "Ator A, Ator B, Atriz A, Atriz B")

        val movies = mutableListOf<Movie>()

        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = MovieAdapter(movies, R.layout.movie_item_similar)

        val toolbar: Toolbar = findViewById(R.id.movie_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        val layerDrawable: LayerDrawable =
            ContextCompat.getDrawable(this, R.drawable.shadows) as LayerDrawable
        val movieCover = ContextCompat.getDrawable(this, R.drawable.movie_4)

        layerDrawable.setDrawableByLayerId(R.id.cover_drawable, movieCover)

        val coverImg: ImageView = findViewById(R.id.movie_img)
        coverImg.setImageDrawable(layerDrawable)
    }
}
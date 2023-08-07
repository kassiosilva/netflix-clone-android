package com.example.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.model.Category
import com.example.netflixremake.model.Movie

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categories = mutableListOf<Category>()

        for (j in 0 until 5) {
            val movies = mutableListOf<Movie>()

            for (i in 0 until 15) {
                val movie = Movie(R.drawable.movie)
                movies.add(movie)
            }

            val category = Category("cat $j", movies)
            categories.add(category)
        }

        val adapter = CategoryAdapter(categories)
        val recyclerMain: RecyclerView = findViewById(R.id.recycler_main)
        recyclerMain.layoutManager = LinearLayoutManager(this)
        recyclerMain.adapter = adapter
    }
}
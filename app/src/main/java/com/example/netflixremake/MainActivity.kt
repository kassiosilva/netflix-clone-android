package com.example.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.model.Category
import com.example.netflixremake.util.CategoryTask

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categories = mutableListOf<Category>()

        val adapter = CategoryAdapter(categories)
        val recyclerMain: RecyclerView = findViewById(R.id.recycler_main)
        recyclerMain.layoutManager = LinearLayoutManager(this)
        recyclerMain.adapter = adapter

        CategoryTask().execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=f95c1bcb-03c1-48d3-b461-e38402a7ecb5")
    }
}
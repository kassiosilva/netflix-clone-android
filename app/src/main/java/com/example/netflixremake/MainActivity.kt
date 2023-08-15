package com.example.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.model.Category
import com.example.netflixremake.util.CategoryTask

class MainActivity : AppCompatActivity(), CategoryTask.Callback {

    private lateinit var progress: ProgressBar
    private lateinit var adapter: CategoryAdapter
    private val categories = mutableListOf<Category>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = findViewById(R.id.progress_main)

        adapter = CategoryAdapter(categories)
        val recyclerMain: RecyclerView = findViewById(R.id.recycler_main)
        recyclerMain.layoutManager = LinearLayoutManager(this)
        recyclerMain.adapter = adapter

        CategoryTask(this).execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=f95c1bcb-03c1-48d3-b461-e38402a7ecb5")
    }

    override fun onPreExecute() {
        progress.visibility = View.VISIBLE
    }

    override fun onResult(categories: List<Category>) {
        this.categories.clear()
        this.categories.addAll(categories)
        adapter.notifyDataSetChanged()

        progress.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        progress.visibility = View.GONE
    }
}
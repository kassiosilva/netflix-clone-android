package com.example.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainAdapter()
        val recyclerMain: RecyclerView = findViewById(R.id.recycler_main)
        recyclerMain.layoutManager = LinearLayoutManager(this)
        recyclerMain.adapter = adapter
    }
}
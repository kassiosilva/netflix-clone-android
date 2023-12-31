package com.example.netflixremake.util

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.netflixremake.model.Category
import com.example.netflixremake.model.Movie
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask(private val callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()

    interface Callback {
        fun onPreExecute()
        fun onResult(categories: List<Category>)
        fun onFailure(message: String)
    }

    fun execute(url: String) {
        callback.onPreExecute()

        executor.execute {
            var urlConnection: HttpsURLConnection? = null
            var stream: InputStream? = null

            try {
                val requestUrl = URL(url) // Define a URL
                urlConnection =
                    requestUrl.openConnection() as HttpsURLConnection // Abri a conexão. É necessário o casting se a url for https
                urlConnection.readTimeout = 2000 // tempo de leitura
                urlConnection.connectTimeout = 2000

                val statusCode = urlConnection.responseCode

                if (statusCode > 400) {
                    throw IOException("Erro na comunicação com o servidor!")
                }

                stream = urlConnection.inputStream
                val jsonAsString = stream.bufferedReader().use { it.readText() }

                val categories = toCategories(jsonAsString)

                handler.post {
                    callback.onResult(categories)
                }
            } catch (e: IOException) {
                val message = e.message ?: "erro desconhecido"
                Log.e("Teste", message, e)
                handler.post {
                    callback.onFailure(message)
                }

            } finally {
                urlConnection?.disconnect()
                stream?.close()
            }
        }
    }

    private fun toCategories(jsonAsString: String): List<Category> {
        val categories = mutableListOf<Category>()

        val jsonRoot = JSONObject(jsonAsString)
        val jsonCategories = jsonRoot.getJSONArray("category")

        for (i in 0 until jsonCategories.length()) {
            val jsonCategory = jsonCategories.getJSONObject(i)

            val title = jsonCategory.getString("title")
            val jsonMovies = jsonCategory.getJSONArray("movie")

            val movies = mutableListOf<Movie>()

            for (j in 0 until jsonMovies.length()) {
                val jsonMovie = jsonMovies.getJSONObject(j)
                val id = jsonMovie.getInt("id")
                val coverUrl = jsonMovie.getString("cover_url")

                movies.add(Movie(id, coverUrl))
            }

            categories.add(Category(title, movies))
        }

        return categories
    }
}
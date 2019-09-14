package com.xalles.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import java.net.URL
import com.google.gson.Gson
import com.xalles.newsap.models.ApiResponse
import com.xalles.newsap.models.Article
import com.xalles.newsapp.adapters.NewsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


// This activity will show the main list

class MainActivity : AppCompatActivity() {

    private val apiKey = ""

    private lateinit var adapter: NewsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var articleList: ArrayList<Article> = ArrayList()
    private var gson = Gson()


    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
        fetchData()

    }

    private fun initialize() {
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_news.layoutManager = linearLayoutManager

        adapter = NewsAdapter(articleList)
        rv_news.adapter = adapter

        swipeContainer.setOnRefreshListener {
            fetchData()
        }
    }

    private fun fetchData() {
        val deferred = GlobalScope.launch {
            var jsonReturn = URL("https://newsapi.org/v2/top-headlines?country=ca&apiKey=$apiKey").readText()

            var data = gson.fromJson(jsonReturn, ApiResponse::class.java)

            articleList.clear()
            articleList.addAll(data.articles as ArrayList<Article>)

            runOnUiThread {
                adapter.notifyDataSetChanged()

                swipeContainer.isRefreshing = false
            }
        }
    }
}

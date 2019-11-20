package com.xalles.newsapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import android.transition.Explode
import android.transition.Slide
import android.transition.TransitionInflater
import java.net.URL
import com.google.gson.Gson
import com.xalles.newsapp.model.ApiResponse
import com.xalles.newsapp.model.Article
import com.xalles.newsapp.R
import com.xalles.newsapp.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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

        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            sharedElementExitTransition = Slide()
        }

        setContentView(R.layout.activity_main)

        initialize()
        fetchData()

    }

    private fun initialize() {

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_news.layoutManager = linearLayoutManager

        adapter = NewsAdapter(articleList, this@MainActivity)
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

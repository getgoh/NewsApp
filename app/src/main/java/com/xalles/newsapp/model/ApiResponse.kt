package com.xalles.newsapp.model


data class ApiResponse(val status: String, val totalStatus: Int, val articles: MutableList<Article>)
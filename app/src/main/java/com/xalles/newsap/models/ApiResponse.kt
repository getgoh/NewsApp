package com.xalles.newsap.models

data class ApiResponse(val status: String, val totalStatus: Int, val articles: MutableList<Article>)
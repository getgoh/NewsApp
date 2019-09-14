package com.xalles.newsapp.adapters

import android.net.Uri
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xalles.newsap.models.Article
import com.xalles.newsapp.R
import com.xalles.newsapp.inflate
import kotlinx.android.synthetic.main.news_list_item.view.*

class NewsAdapter(private val articles: ArrayList<Article>) : RecyclerView.Adapter<ArticleHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val inflatedView = parent.inflate(R.layout.news_list_item, false)
        return ArticleHolder(inflatedView)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val itemArticle = articles[position]
        holder.bindArticle(itemArticle)
    }
}

class ArticleHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

    private var view: View = v
    private var _article: Article? = null

    init {
        v.setOnClickListener(this)
    }

    fun bindArticle(article: Article){
       this._article = article

        view.news_holder.setOnLongClickListener {
            view.news_holder.isChecked = !view.news_holder.isChecked
            true
        }

        if (article.author != null)
        {
            view.news_item_author.text = article.author
        }
        else
        {
            view.news_item_author.visibility = View.GONE
        }

        view.news_item_title.text = article.title

        Glide.with(view)
            .load(article.urlToImage)
            .fitCenter()
            .into(view.news_item_img)

    }

    override fun onClick(v: View) {
        Log.d("RecyclerView", "Article title: ${this._article?.title}")
    }
    companion object {
        private val ARTICLE_KEY = "ARTICLE"
    }

}
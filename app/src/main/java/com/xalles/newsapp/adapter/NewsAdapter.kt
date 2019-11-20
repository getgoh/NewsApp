package com.xalles.newsapp.adapter

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.xalles.newsapp.model.Article
import com.xalles.newsapp.R
import com.xalles.newsapp.helper.inflate
import kotlinx.android.synthetic.main.news_list_item.view.*

import androidx.core.util.*
import androidx.core.view.ViewCompat
import com.xalles.newsapp.activity.DetailsActivity

class NewsAdapter(private val articles: ArrayList<Article>, private val context: Context) : RecyclerView.Adapter<ArticleHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val inflatedView = parent.inflate(R.layout.news_list_item, false)
        return ArticleHolder(inflatedView, context)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val itemArticle = articles[position]
        holder.bindArticle(itemArticle)
    }

    fun clear() {
        articles.clear()
        notifyDataSetChanged()
    }

    fun addAll(_articles: ArrayList<Article>) {
        articles.addAll(_articles)
        notifyDataSetChanged()
    }

}

class ArticleHolder(v: View, c: Context) : RecyclerView.ViewHolder(v), View.OnClickListener {

    private var view: View = v
    private var _article: Article? = null
    private var sizeProvider = ViewPreloadSizeProvider<String>()
    private var context: Context = c

    private var clicked = false

    init {
        v.setOnClickListener(this)
    }

    fun bindArticle(article: Article){
       this._article = article

        view.news_holder.setOnLongClickListener {
            view.news_holder.isChecked = !view.news_holder.isChecked
            true
        }

        view.news_item_author.text = article.author
        view.news_item_author.visibility = if (article.author != null) View.VISIBLE else View.GONE

        view.news_item_title.text = article.title

        Glide.with(view)
            .load(article.urlToImage)
            .fitCenter()
            .into(view.news_item_img)

        ViewCompat.setTransitionName(view.news_item_img, "ASDFG")

    }

    override fun onClick(v: View) {

        val i = Intent(context, DetailsActivity::class.java)
        i.putExtra("article", _article)
        i.putExtra("transitionName", ViewCompat.getTransitionName(v.news_item_img))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                v.news_item_img, ViewCompat.getTransitionName(v.news_item_img)!!
            )

            ActivityCompat.startActivity(context, i, options.toBundle())
        }
//
//        clicked = !clicked
//
//        var heightDiff = 400
//        if (!clicked) {
//            heightDiff = -400
//        }
//        val valueAnimator = ValueAnimator.ofInt(v.measuredHeight, v.measuredHeight + heightDiff)
//
////2
//        valueAnimator.addUpdateListener {
//            // 3
//            val value = it.animatedValue as Int
//            // 4
//            val layoutParams:ViewGroup.LayoutParams = v.layoutParams
//            layoutParams.height = value
//            v.layoutParams = layoutParams
//        }
//
////5
////        valueAnimator.interpolator = LinearInterpolator()
//        valueAnimator.duration = 300
//
////6
//        valueAnimator.start()
    }
    companion object {
        private val ARTICLE_KEY = "ARTICLE"
    }

}
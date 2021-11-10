package com.articles.popular.vm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.articles.popular.R
import com.articles.popular.model.Article
import kotlinx.android.synthetic.main.article_item_row.view.*

class ArticleAdapter( var articles: ArrayList<Article>): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item_row, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article            = articles[position]
        holder.title.text      = article.title
        holder.auther.text     = article.byline
        holder.date.text       = article.updated.substring(0,10)
        holder.subsection.text = if(article.subsection=="") article.section else article.subsection
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var title      : TextView = itemView.title
        internal var auther     : TextView = itemView.auther
        internal var date       : TextView = itemView.date
        internal var subsection : TextView = itemView.subsection
    }


}
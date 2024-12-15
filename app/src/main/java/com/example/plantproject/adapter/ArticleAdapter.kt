package com.example.plantproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantproject.R
import com.example.plantproject.model.Article

class ArticleAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.rvArticleImg)
        val titleView: TextView = itemView.findViewById(R.id.rvArticleTitle)
        val textView: TextView = itemView.findViewById(R.id.rvArticleText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false) // Pastikan nama file layout sesuai
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.titleView.text = article.title
        holder.textView.text = article.text
        Glide.with(holder.imageView.context)
            .load(article.imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = articles.size
}

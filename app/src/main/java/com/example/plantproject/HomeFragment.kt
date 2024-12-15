package com.example.plantproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantproject.adapter.ArticleAdapter
import com.example.plantproject.retrofit.RetrofitClientArticle
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val articleApi = RetrofitClientArticle.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inisialisasi RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Ambil Artikel dari API
        fetchArticles()

        return view
    }

    override fun onStart() {
        super.onStart()
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val email = sharedPref.getString("user_email", null)
        val password = sharedPref.getString("user_password", null)

        if (email == null || password == null) {
            if (findNavController().currentDestination?.id == R.id.homeFragment) {
                findNavController().navigate(R.id.notLoggedIn)
            }
        }
    }

    // Fetch all articles
    private fun fetchArticles() {
        lifecycleScope.launch {
            try {
                val articles = articleApi.getArticle()
                recyclerView.adapter = ArticleAdapter(articles)
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error fetching articles: ${e.message}")
                Toast.makeText(requireContext(), "Failed to load articles", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    // Create a new article
//    private fun createArticle() {
//        val newArticle = Article(
//            id = null, // ID otomatis di-generate oleh MongoDB
//            title = "New Article Title",
//            text = "This is the content of the new article.",
//            imageUrl = "https://via.placeholder.com/150" // URL gambar dari server online
//        )
//
//        lifecycleScope.launch {
//            try {
//                val createdArticle = articleApi.createArticle(newArticle)
//                Toast.makeText(requireContext(), "Article created: ${createdArticle.title}", Toast.LENGTH_SHORT).show()
//                fetchArticles() // Refresh list
//            } catch (e: Exception) {
//                Log.e("HomeFragment", "Error creating article: ${e.message}")
//            }
//        }
//    }
//
//    // Update an article
//    private fun updateArticle(articleId: String) {
//        val updatedArticle = Article(
//            id = articleId, // ID artikel yang akan diperbarui
//            title = "Updated Article Title",
//            text = "This is the updated content of the article.",
//            imageUrl = "https://via.placeholder.com/150"
//        )
//
//        lifecycleScope.launch {
//            try {
//                val response = articleApi.updateArticle(articleId, updatedArticle)
//                Toast.makeText(requireContext(), "Article updated: ${response.title}", Toast.LENGTH_SHORT).show()
//                fetchArticles() // Refresh list
//            } catch (e: Exception) {
//                Log.e("HomeFragment", "Error updating article: ${e.message}")
//            }
//        }
//    }
//
//    // Delete an article
//    private fun deleteArticle(articleId: String) {
//        lifecycleScope.launch {
//            try {
//                articleApi.deleteArticle(articleId)
//                Toast.makeText(requireContext(), "Article deleted", Toast.LENGTH_SHORT).show()
//                fetchArticles() // Refresh list
//            } catch (e: Exception) {
//                Log.e("HomeFragment", "Error deleting article: ${e.message}")
//            }
//        }
//    }
//
//    // Get details of a single article
//    private fun getArticleDetail(articleId: String) {
//        lifecycleScope.launch {
//            try {
//                val article = articleApi.getArticleDetail(articleId)
//                Log.d("HomeFragment", "Article details: $article")
//            } catch (e: Exception) {
//                Log.e("HomeFragment", "Error fetching article details: ${e.message}")
//            }
//        }
//    }
}

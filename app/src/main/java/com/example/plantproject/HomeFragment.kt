package com.example.plantproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantproject.R
import com.example.plantproject.adapter.Article
import com.example.plantproject.adapter.ArticleAdapter



    class HomeFragment : Fragment() {

        private lateinit var recyclerView: RecyclerView

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_home, container, false)

            // Inisialisasi RecyclerView
            recyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)

            // Dummy Data
            val articles = listOf(
                Article(
                    image = "https://via.placeholder.com/150",
                    title = "Article 1",
                    text = "This is the first article."
                ),
                Article(
                    image = "https://via.placeholder.com/150",
                    title = "Article 2",
                    text = "This is the second article."
                )
            )

            // Set Adapter
            recyclerView.adapter = ArticleAdapter(articles)

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

        // Check if user is not logged in
        if (email == null || password == null) {
            if (findNavController().currentDestination?.id == R.id.homeFragment) {
                findNavController().navigate(R.id.notLoggedIn)
            }
        }
    }
}

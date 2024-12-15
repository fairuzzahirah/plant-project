package com.example.plantproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantproject.adapter.ProductAdapter
import com.example.plantproject.model.Product

class PlantCareFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView // Make sure to define this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plant_care, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)

        // Set the GridLayoutManager with 3 columns
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Dummy Data
        val products = listOf(
            Product(
                _id = "1",
                image = "https://via.placeholder.com/150",
                name = "Plant Care Article 1",
                price = 50.0,
                detail = "Merawat tanaman hias di dalam rumah adalah cara yang efektif..."
            ),
            Product(
                _id = "2",
                image = "https://via.placeholder.com/150",
                name = "Plant Care Article 2",
                price = 30.0,
                detail = "This article explains the best practices for indoor plants."
            ),
            Product(
                _id = "3",
                image = "https://via.placeholder.com/150",
                name = "Plant Care Article 3",
                price = 40.0,
                detail = "Learn how to water your plants effectively for better growth."
            ),
            Product(
                _id = "4",
                image = "https://via.placeholder.com/150",
                name = "Plant Care Article 4",
                price = 60.0,
                detail = "Tips on pruning and shaping your indoor plants."
            )
        )
        recyclerView.adapter = ProductAdapter(products)

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
            // Ensure navigation only happens if on PlantCareFragment
            if (findNavController().currentDestination?.id == R.id.plantCareFragment) {
                findNavController().navigate(R.id.notLoggedIn)
            }
        }
    }
}

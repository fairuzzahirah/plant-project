package com.example.plantproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class PlantCareFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant_care, container, false)
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
            // Pastikan navigasi hanya terjadi jika berada di PlantCareFragment
            if (findNavController().currentDestination?.id == R.id.plantCareFragment) {
                findNavController().navigate(R.id.notLoggedIn)
            }
        }
    }
}

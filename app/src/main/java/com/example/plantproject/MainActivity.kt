package com.example.plantproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Inisialisasi BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Periksa status login
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

        // Setup navigasi BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            if (isLoggedIn) {
                // Jika sudah login, navigasikan ke fragment yang sesuai
                when (item.itemId) {
                    R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                    R.id.planFragment -> navController.navigate(R.id.planFragment)
                    R.id.wishlistFragment -> navController.navigate(R.id.wishlistFragment)
                    R.id.profileFragment -> navController.navigate(R.id.profileFragment)
                }
            } else {
                // Jika belum login, hanya HomeFragment yang dapat diakses
                when (item.itemId) {
                    R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                    else -> navController.navigate(R.id.notLoggedIn)
                }
            }
            true
        }

        // Set fragment default saat pertama kali dibuka
        if (savedInstanceState == null) {
            if (isLoggedIn) {
                navController.navigate(R.id.homeFragment) // Fragment default jika sudah login
            }
        }
    }
}

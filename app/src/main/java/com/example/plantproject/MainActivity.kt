package com.example.plantproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.plantproject.database.DatabaseHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Inisialisasi DatabaseHelper
        dbHelper = DatabaseHelper(this)

        // Cek status login dengan SharedPreferences
        val isLoggedIn = checkIfUserLoggedIn()

        // Setup navigasi BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            if (isLoggedIn) {
                // Jika sudah login, navigasikan ke fragment yang sesuai
                when (item.itemId) {
                    R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                    R.id.planFragment -> navController.navigate(R.id.plantCareFragment)
                    R.id.wishlistFragment -> navController.navigate(R.id.wishlistFragment)
                    R.id.profileFragment -> navController.navigate(R.id.profileFragment)
                }
            } else {
                // Jika belum login, hanya HomeFragment yang dapat diakses
                when (item.itemId) {
                    R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                    else -> navController.navigate(R.id.notLoggedIn)  // Navigasi ke fragment notLoggedIn
                }
            }
            true
        }

        // Set fragment default saat pertama kali dibuka
        if (savedInstanceState == null) {
            if (isLoggedIn) {
                navController.navigate(R.id.homeFragment)
            } else {
                navController.navigate(R.id.notLoggedIn)
            }
        }
    }

    private fun checkIfUserLoggedIn(): Boolean {
        // Mengecek status login menggunakan SharedPreferences
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val email = sharedPref.getString("user_email", null)
        val password = sharedPref.getString("user_password", null)

        return email != null && password != null && dbHelper.checkUser(email, password)
    }
}

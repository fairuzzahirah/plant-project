package com.example.plantproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantproject.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menampilkan data username dan age dari SharedPreferences
        showUserProfile()

        binding.btnLogout.setOnClickListener {
            // Hapus data pengguna dari SharedPreferences
            logoutUser()

            // Arahkan ke LoginFragment
            val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val email = sharedPref.getString("user_email", null) // null jika belum ada data
        val password = sharedPref.getString("user_password", null)

        if (email == null || password == null) {
            // Hanya arahkan jika saat ini berada di ProfileFragment
            if (findNavController().currentDestination?.id == R.id.profileFragment) {
                findNavController().navigate(R.id.notLoggedIn)
            }
        }
    }

    private fun logoutUser() {
        // Hapus data pengguna dari SharedPreferences
        val sharedPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove("user_email")
        editor.remove("user_password")
        editor.apply()

        // Menampilkan pesan Toast
        Toast.makeText(activity, "Logout Successful!", Toast.LENGTH_SHORT).show()
    }

    private fun showUserProfile() {
        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Ambil data username dan age dari SharedPreferences
        val username = sharedPref.getString("user_name", "Tidak ada username")
        val email = sharedPref.getString("user_email","Email Belum Terverifikasi")

        // Menampilkan data di TextView
        val textViewUsername: TextView = binding.usernameProfile
        val textViewEmail: TextView = binding.emailProfile

        textViewUsername.text = "Username: $username"
        textViewEmail.text = "Email: $email"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

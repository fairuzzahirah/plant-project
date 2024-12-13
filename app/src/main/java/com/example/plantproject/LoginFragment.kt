package com.example.plantproject

import android.content.Context
import com.example.plantproject.database.DatabaseHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantproject.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())

        with(binding) {
            btnLogin.setOnClickListener {
                val email = binding.editemail.text.toString().trim()
                val password = binding.editpassword.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please enter email and password",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                // Cek login dengan SQLite
                if (dbHelper.checkUser(email, password)) {
                    // Simpan status login di SharedPreferences
                    val sharedPref =
                        requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("user_email", email)
                        putString("user_password", password)
                        apply()
                    }

                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()

                    // Navigasi setelah login sukses
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    findNavController().navigate(action)
                    findNavController().navigate(R.id.homeFragment)
                    findNavController().navigate(R.id.profileFragment)
                    findNavController().navigate(R.id.plantCareFragment)
                    findNavController().navigate(R.id.wishlistFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Invalid email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            txtToRegister.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

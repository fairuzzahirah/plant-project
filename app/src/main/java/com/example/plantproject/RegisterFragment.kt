package com.example.plantproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.plantproject.database.DatabaseHelper
import com.example.plantproject.databinding.FragmentRegisterBinding
import com.example.plantproject.model.AuthResponse
import com.example.plantproject.model.User
import com.example.plantproject.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())

        with(binding) {
            btnRegister.setOnClickListener {
                val username = editusername.text.toString().trim()
                val email = editemail.text.toString().trim()
                val password = editpassword.text.toString().trim()

                // Validasi input
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val user = User(email, password, username)

                RetrofitClient.instance.register(user).enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                        if (response.isSuccessful) {
                            val message = response.body()?.message
                            if (message == "Data Created Successfully!") {
                                dbHelper.insertUser(user)

                                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                                findNavController().navigate(action)
                            } else {
                                Toast.makeText(requireContext(), "Registration Failed: $message", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }


                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

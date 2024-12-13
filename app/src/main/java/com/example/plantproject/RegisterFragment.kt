package com.example.plantproject

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

                val user = User(username, email, password)

                // Kirim data ke API register
                RetrofitClient.instance.register(user).enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(
                        call: Call<AuthResponse>,
                        response: Response<AuthResponse>
                    ) {
                        if (response.isSuccessful && response.body()?.status == "success") {
                            Toast.makeText(activity, "Registration Successful", Toast.LENGTH_SHORT).show()

                            // Simpan data pengguna ke Shared Preferences
                            saveUserToPreferences(username, email)

                            // Navigasi ke halaman login (jika ada)
                        } else {
                            Toast.makeText(activity, "Registration Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        Toast.makeText(activity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun saveUserToPreferences(username: String, email: String) {
        // Mengakses Shared Preferences
        val sharedPreferences = activity?.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        // Menyimpan data
        editor?.putString("username", username)
        editor?.putString("email", email)
        editor?.apply()

        Toast.makeText(activity, "User data saved locally!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.plantproject

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantproject.databinding.FragmentLoginBinding
import com.example.plantproject.model.AuthResponse
import com.example.plantproject.model.LoginRequest
import com.example.plantproject.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnLogin.setOnClickListener {
                val email = editemail.text.toString().trim()
                val password = editpassword.text.toString().trim()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    val loginRequest = LoginRequest(email, password)

                    // Mengirim permintaan login ke server
                    RetrofitClient.instance.login(loginRequest).enqueue(object : Callback<AuthResponse> {
                        override fun onResponse(
                            call: Call<AuthResponse>,
                            response: Response<AuthResponse>
                        ) {
                            if (response.isSuccessful && response.body()?.status == "success") {
                                // Jika login berhasil, simpan token ke SharedPreferences
                                val token = response.body()?.token
                                val sharedPref = activity?.getSharedPreferences("user_pref", MODE_PRIVATE)
                                sharedPref?.edit()?.putString("auth_token", token)?.apply()

                                Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show()

                                // Navigasi ke HomeFragment
                                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                                findNavController().navigate(action)
                            } else {
                                // Jika login gagal, tampilkan pesan error
                                Toast.makeText(activity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                            Toast.makeText(activity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show()
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

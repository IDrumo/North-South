package com.project.north_south

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.project.north_south.databinding.ActivityLoginPageBinding
import models.UserLoginRequest
import models.UserLoginResponse
import network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginPage : AppCompatActivity() {
    lateinit var binding: ActivityLoginPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val api = retrofit.create(ApiService::class.java)

        val intent = Intent(this, AccountMenu::class.java)

        binding.loginButton.setOnClickListener {
            val login = binding.loginField.editText?.text.toString().trim()
            val password = binding.passwordField.editText?.text.toString()

            if (login == "" || password == "") {
                if (login == "") binding.loginField.setError(getString(R.string.login_errer_message))
                if (password == "") binding.passwordField.setError(getString(R.string.password_errer_message))
                Toast.makeText(this, R.string.toast_error_message, Toast.LENGTH_SHORT).show()
            } else {
                // если будет ошибка, то можно будет попробовать отправить не user, а 2 строки
                val response = api.loginUser(UserLoginRequest(login, password))
                    .enqueue(object : Callback<UserLoginResponse> {

                        override fun onResponse(
                            call: Call<UserLoginResponse>,
                            response: Response<UserLoginResponse>
                        ) {

                            if (response.isSuccessful) {

                                intent.putExtra("login", login)
                                    .putExtra("password", password)
                                    .putExtra("token", response.body()?.token)
                                    .putExtra("role", response.body()?.role)
                                Log.d("MyLog", "LKJAGFLKJASH")
                                startActivity(intent)
                                finish()

                            } else {
                                Log.d("MyLog", getString(R.string.not_found_error_message))
                            }
                        }

                            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                                Log.d("MyLog", getString(R.string.conection_error_message))
                            }

                        })
                    }
            }

        }


    }


package com.project.north_south

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.north_south.databinding.ActivityLoginPageBinding
import models.UserLoginRequest
import models.UserLoginResponse
import network.InitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val api = InitAPI(getString(R.string.base_url)).getAPI()

        val intent = Intent(this, AccountMenu::class.java)

        binding.loginButton.setOnClickListener {
            val login = binding.loginField.editText?.text.toString().trim()
            val password = binding.passwordField.editText?.text.toString()

            if (login == "" || password == "") {
                if (login == "") binding.loginField.error = getString(R.string.login_errer_message)
                if (password == "") binding.passwordField.error = getString(R.string.password_errer_message)
                Toast.makeText(this, R.string.toast_error_message, Toast.LENGTH_SHORT).show()
            } else {

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
                                startActivity(intent)
                                finish()

                            } else {
                                Toast.makeText(this@LoginPage, getString(R.string.not_found_error_message), Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                            Toast.makeText(this@LoginPage, getString(R.string.conection_error_message), Toast.LENGTH_LONG).show()
                        }

                    })
            }
        }
    }
}
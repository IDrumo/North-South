package com.project.north_south.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.north_south.R
import com.project.north_south.ViewModels.LoginViewModel
import com.project.north_south.databinding.ActivityLoginPageBinding

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.loginButton.setOnClickListener {
            val login = binding.loginField.editText?.text.toString().trim()
            val password = binding.passwordField.editText?.text.toString()

            if (login == "" || password == "") {
                if (login == "") binding.loginField.error = getString(R.string.login_errer_message)
                if (password == "") binding.passwordField.error =
                    getString(R.string.password_errer_message)
                Toast.makeText(this, R.string.toast_error_message, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.loginUser(login, password)
            }

            viewModel.loginResponse.observe(this) { intent ->
                startActivity(intent)
                finish()
            }
        }
    }
}
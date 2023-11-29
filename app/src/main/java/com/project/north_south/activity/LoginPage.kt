package com.project.north_south.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.north_south.viewModels.LoginViewModel
import com.project.north_south.databinding.ActivityLoginPageBinding

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.tryEnterAuto()

        binding.loginButton.setOnClickListener {
            viewModel.tryEnter(binding)
        }
        viewModel.startAccountMenuEvent.observe(this) {
            startActivity(Intent(this, AccountMenu::class.java))
            finish()
        }
    }
}
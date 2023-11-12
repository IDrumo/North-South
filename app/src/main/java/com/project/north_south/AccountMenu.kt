package com.project.north_south

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.north_south.databinding.ActivityAccountMenuBinding
import com.project.north_south.databinding.ActivityLoginPageBinding

class AccountMenu : AppCompatActivity() {
    lateinit var binding: ActivityAccountMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val login = intent.getStringExtra("login")
        val password = intent.getStringExtra("password")
        binding.textView2.text = "login = ${login} \n password = ${password}"
    }
}
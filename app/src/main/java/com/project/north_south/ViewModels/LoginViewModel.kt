package com.project.north_south.ViewModels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.project.north_south.R
import com.project.north_south.databinding.ActivityLoginPageBinding
import network.InitAPI
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class LoginViewModel(val context: Application) : AndroidViewModel(context) {
    val startAccountMenuEvent: MutableLiveData<StartAccountMenuEvent> = MutableLiveData()

    class StartAccountMenuEvent

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    fun tryEnterAuto() {
        val login = sharedPreferences.getString("login", "")
        val password = sharedPreferences.getString("password", "")
        if (login != null && password != null) {
            if (login.isNotEmpty() && password.isNotEmpty())
                loginUser(login, password)
        }
    }

    fun tryEnter(binding: ActivityLoginPageBinding) {
        val login = binding.loginField.editText?.text.toString().trim()
        val password = binding.passwordField.editText?.text.toString()

        if (login == "" || password == "") {
            if (login == "") binding.loginField.error =
                context.getString(R.string.login_errer_message)
            if (password == "") binding.passwordField.error =
                context.getString(R.string.password_errer_message)
            Toast.makeText(context, R.string.toast_error_message, Toast.LENGTH_SHORT).show()
        } else {
            loginUser(login, password)
        }
    }

    private fun loginUser(login: String, password: String) {
        InitAPI(context.getString(R.string.base_url)).loginUser(context, login, password)
        Handler().postDelayed({
            if (sharedPreferences.getBoolean("active", false)) {
                startAccountMenuEvent.value = StartAccountMenuEvent()
            }
        }, 1000)
    }

}
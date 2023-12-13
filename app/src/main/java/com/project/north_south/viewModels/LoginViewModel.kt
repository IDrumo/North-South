package com.project.north_south.viewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.project.north_south.R
import com.project.north_south.databinding.ActivityLoginPageBinding
import com.project.north_south.subAlgorithms.ErrorMessage
import com.project.north_south.subAlgorithms.Storage
import models.UserLoginResponse
import network.InitAPI


class LoginViewModel(val context: Application) : AndroidViewModel(context) {
    val startAccountMenuEvent: MutableLiveData<StartAccountMenuEvent> = MutableLiveData()
    class StartAccountMenuEvent

    private val storage = Storage(context)
    private val apiService = InitAPI()

    fun tryEnterAuto() {
        val (login, password) = storage.getUserLoginPassword()
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
            ErrorMessage(context).empty_fields_error()
        } else {
            loginUser(login, password)
        }
    }

    private fun loginUser(login: String, password: String) {
        apiService.loginUser(login, password, object : InitAPI.LoginCallback {
            override fun onSuccess(response: UserLoginResponse) {
                storage.saveUserInfo(login, password, response)
                if (storage.getUserStatus()) {
                    startAccountMenuEvent.value = StartAccountMenuEvent()
                }
            }

            override fun onError() {
                ErrorMessage(context).not_found_error()
            }

            override fun onFailure(error: Throwable) {
                ErrorMessage(context).connection_error()
            }
        })
    }

}
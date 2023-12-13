package com.project.north_south.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.project.north_south.R
import com.project.north_south.databinding.FragmentAccountBinding
import com.project.north_south.subAlgorithms.ErrorMessage
import com.project.north_south.subAlgorithms.Storage
import models.UserLoginResponse
import network.InitAPI

class AccountFragmentViewModel(val context: Application) : AndroidViewModel(context) {
    private val storage = Storage(context)
    private val apiService = InitAPI()
    val startLoginEvent: MutableLiveData<StartLoginEvent> = MutableLiveData()

    class StartLoginEvent

    fun reloadUser(binding: FragmentAccountBinding) {
        val (login, password) = storage.getUserLoginPassword()
        if (password != null && login != null) {
            loginUser(login, password, binding)
        }
    }

    fun updateUserInfo(binding: FragmentAccountBinding) {
        val user = storage.getUser()
        binding.apply {
            firstName.text = user.first_name
            lastName.text = user.last_name
            patronymic.text = user.patronymic
            busCode.text = user.bus_code
        }
    }

    private fun loginUser(login: String, password: String, binding: FragmentAccountBinding) {
        apiService.loginUser(login, password, object : InitAPI.LoginCallback {
            override fun onSuccess(response: UserLoginResponse) {

                storage.saveUserInfo(login, password, response)
                if (storage.getUserStatus()) {
                    updateUserInfo(binding)
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

    fun exit() {
        storage.clearAll()
        startLoginEvent.value = StartLoginEvent()
    }
}
package com.project.north_south.viewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.project.north_south.R
import com.project.north_south.databinding.FragmentAccountBinding
import com.project.north_south.subAlgorithms.Storage
import models.FullUserInfo
import network.InitAPI

class AccountFragmentViewModel(val context: Application) : AndroidViewModel(context){
    private val storage = Storage(context)
    val startLoginEvent: MutableLiveData<StartLoginEvent> = MutableLiveData()
    class StartLoginEvent

    fun reloadUser(binding: FragmentAccountBinding){
        val (login, password) = storage.getUserLoginPassword()
        if (password != null && login != null) {
                loginUser(login, password)
        }
        updateUserInfo(binding)
    }

    fun updateUserInfo(binding: FragmentAccountBinding){
        val user = storage.getUser()
        binding.apply {
            firstName.text = user.first_name
            lastName.text = user.last_name
            patronymic.text = user.patronymic
            busCode.text = user.bus_code
        }
    }

    private fun loginUser(login: String, password: String){
        InitAPI(context.getString(R.string.base_url)).loginUser(login, password, context)
    }

    fun exit(){
//        storage.clearUserData()
        storage.clearAll()
        startLoginEvent.value = StartLoginEvent()
    }
}
package com.project.north_south.ViewModels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.project.north_south.R
import com.project.north_south.databinding.FragmentAccountBinding
import models.FullUserInfo
import network.InitAPI

class AccountFragmentViewModel(val context: Application) : AndroidViewModel(context){
    val userData: MutableLiveData<FullUserInfo> = MutableLiveData()
    val startLoginEvent: MutableLiveData<StartLoginEvent> = MutableLiveData()
    class StartLoginEvent
    val loginResponse: MutableLiveData<FullUserInfo> = MutableLiveData()
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    fun userDataInit(user: FullUserInfo){
        userData.value = user
    }

    fun reloadUser(binding: FragmentAccountBinding){
        val login = sharedPreferences.getString("login", "")
        val password = sharedPreferences.getString("password", "")
        if (password != null && login != null) {
                loginUser(login, password)
        }
        updateUserInfo(binding)
    }

    fun updateUserInfo(binding: FragmentAccountBinding){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        binding.apply {
            firstName.text = sharedPreferences.getString("first_name", "")
            lastName.text = sharedPreferences.getString("last_name", "")
            patronymic.text = sharedPreferences.getString("patronymic", "")
            busCode.text = sharedPreferences.getString("bus_code", "")
        }
    }

    private fun loginUser(login: String, password: String){
        InitAPI(context.getString(R.string.base_url)).loginUser(context, login, password)
    }

    fun exit(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        startLoginEvent.value = StartLoginEvent()
    }
}
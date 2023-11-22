package com.project.north_south.ViewModels

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.project.north_south.R
import com.project.north_south.activity.AccountMenu
import models.UserLoginRequest
import models.UserLoginResponse
import network.InitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(val context: Application) : AndroidViewModel(context) {
    val loginResponse: MutableLiveData<Intent> = MutableLiveData()

    fun loginUser(login: String, password: String) {
        val api = InitAPI(context.getString(R.string.base_url)).getAPI()

        api.loginUser(UserLoginRequest(login, password))
            .enqueue(object : Callback<UserLoginResponse> {
                override fun onResponse(
                    call: Call<UserLoginResponse>,
                    response: Response<UserLoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val intent = Intent(context, AccountMenu::class.java)
                        intent.putExtra("login", login)
                            .putExtra("password", password)
                            .putExtra("token", response.body()?.token)
                            .putExtra("role", response.body()?.role)
                            .putExtra("first_name", response.body()?.first_name)
                            .putExtra("last_name", response.body()?.last_name)
                            .putExtra("patronymic", response.body()?.patronymic)
                        loginResponse.value = intent
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.not_found_error_message),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

                override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.conection_error_message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
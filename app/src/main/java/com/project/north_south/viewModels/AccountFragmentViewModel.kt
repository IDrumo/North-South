package com.project.north_south.viewModels

import NotificationScheduler
import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.project.north_south.R
import com.project.north_south.databinding.FragmentAccountBinding
import com.project.north_south.fragments.ConfirmationFragment
import com.project.north_south.subAlgorithms.ErrorMessage
import com.project.north_south.subAlgorithms.Storage
import models.GetScheduleResponse
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
            ErrorMessage(context).reload_success()
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
            override fun onSuccess(response: GetScheduleResponse) {

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

    fun exit(childFragmentManager: FragmentManager, requireContext: Context) {
        if (storage.tripStarted()) {
            val fragment =
                ConfirmationFragment.newInstance(context.getString(R.string.exit_confirm_txt)) {
                    NotificationScheduler(requireContext).cancelNotifications()
                    storage.clearAll()
                    startLoginEvent.value = StartLoginEvent()
                }
            childFragmentManager.beginTransaction().add(R.id.exitConfirmFragmentPlace, fragment)
                .commit()
        }else{
            NotificationScheduler(requireContext).cancelNotifications()
            storage.clearAll()
            startLoginEvent.value = StartLoginEvent()
        }
    }
}
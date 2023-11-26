package com.project.north_south.ViewModels

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.service.autofill.UserData
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.project.north_south.R
import com.project.north_south.activity.AccountMenu
import com.project.north_south.databinding.ActivityAccountMenuBinding
import com.project.north_south.fragments.AccountFragment
import com.project.north_south.fragments.RoadmapFragment
import com.project.north_south.fragments.ScannerFragment
import models.FullUserInfo
import models.ScheduleResponse
import models.TripItem
import models.UserLoginRequest
import models.UserLoginResponse
import network.InitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountViewModel(
    private val activity: AppCompatActivity,
    private val fragmentManager: FragmentManager
) : ViewModel() {
    val userData: MutableLiveData<FullUserInfo> = MutableLiveData()

    fun launchAccountFrame() {
        fragmentManager.beginTransaction()
            .replace(R.id.frame_place, AccountFragment.newInstance())
            .commit()
    }

    fun launchRoadmapFrame(list: ArrayList<TripItem>?) {
        fragmentManager.beginTransaction()
            .replace(R.id.frame_place, RoadmapFragment.newInstance(list))
            .commit()
    }

    fun launchScannerFrame() {
        fragmentManager.beginTransaction()
            .replace(R.id.frame_place, ScannerFragment.newInstance())
            .commit()
    }

    fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {

            launchScannerFrame()

        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), 1)
        }
    }


    fun initData(): ArrayList<TripItem>? {
        val api = InitAPI(activity.getString(R.string.base_url)).getAPI()

        var list: ArrayList<TripItem>? = null

        val response = api.getAllSchedule()
            .enqueue(object : Callback<ScheduleResponse> {
                override fun onResponse(
                    call: Call<ScheduleResponse>,
                    response: Response<ScheduleResponse>
                ) {
                    if (response.isSuccessful) {
                        list = response.body()?.scheduleList
                    } else {
                        Toast.makeText(
                            activity,
                            activity.getString(R.string.request_error_message),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ScheduleResponse>, t: Throwable) {
                    Toast.makeText(
                        activity,
                        activity.getString(R.string.server_error_message),
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        return list
    }

}
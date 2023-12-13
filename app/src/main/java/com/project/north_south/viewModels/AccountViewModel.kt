package com.project.north_south.viewModels

import NotificationScheduler
import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import com.project.north_south.R
import com.project.north_south.fragments.AccountFragment
import com.project.north_south.fragments.RoadmapFragment
import com.project.north_south.fragments.ScannerFragment
import com.project.north_south.fragments.TripFragment
import com.project.north_south.subAlgorithms.Storage

class AccountViewModel(context: Application) : AndroidViewModel(context) {

    private val storage = Storage(context)
    private lateinit var fragmentManager: FragmentManager

    fun initFragmentManager(supportFragmentManager: FragmentManager) {
        fragmentManager = supportFragmentManager
    }

    fun initNotification(context: Context){
        val schedule = storage.getRoadmapInfo()
        val eventTimes : ArrayList<String> = arrayListOf()
        schedule.forEach { trip->
            eventTimes.add(trip.trip.departure_time + ":00")
        }

        val notificationScheduler = NotificationScheduler(context)
        notificationScheduler.scheduleNotifications(eventTimes)

    }

    fun launchAccountFrame() {
        fragmentManager.beginTransaction()
            .replace(R.id.frame_place, AccountFragment.newInstance())
            .commit()
    }

    fun launchRoadmapFrame() {
        if (storage.getTripSelectedStatus()) {
            fragmentManager.beginTransaction()
                .replace(R.id.frame_place, TripFragment.newInstance())
                .commit()
        } else {
            fragmentManager.beginTransaction()
                .replace(R.id.frame_place, RoadmapFragment.newInstance())
                .commit()
        }
    }

    fun launchScannerFrame() {
        fragmentManager.beginTransaction()
            .replace(R.id.frame_place, ScannerFragment.newInstance())
            .commit()
    }

    fun checkCameraPermission(activity: Activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            launchScannerFrame()
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), 1)
        }
    }

}
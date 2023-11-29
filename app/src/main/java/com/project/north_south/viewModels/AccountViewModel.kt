package com.project.north_south.viewModels

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.project.north_south.R
import com.project.north_south.fragments.AccountFragment
import com.project.north_south.fragments.RoadmapFragment
import com.project.north_south.fragments.ScannerFragment
import com.project.north_south.fragments.TripFragment

class AccountViewModel(
    private val activity: AppCompatActivity,
    private val fragmentManager: FragmentManager
) : ViewModel() {
//    val userData: MutableLiveData<FullUserInfo> = MutableLiveData()
    private val sharedTrip: SharedPreferences = activity.getSharedPreferences("trip_info", Context.MODE_PRIVATE)
    private val sharedRoadmap: SharedPreferences = activity.getSharedPreferences("roadmap_info", Context.MODE_PRIVATE)

    fun launchAccountFrame() {
        fragmentManager.beginTransaction()
            .replace(R.id.frame_place, AccountFragment.newInstance())
            .commit()
    }

    fun launchRoadmapFrame() {
        if (sharedTrip.getBoolean("sub_frame_part_active", false)) {
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

    fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            launchScannerFrame()
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), 1)
        }
    }

    fun clearData(){
        sharedTrip.edit().clear().apply()
    }


//    fun initData(): ArrayList<TripItem>? {
//        val api = InitAPI(activity.getString(R.string.base_url)).getAPI()
//
//        var list: ArrayList<TripItem>? = null
//
//        val response = api.getAllSchedule()
//            .enqueue(object : Callback<ScheduleResponse> {
//                override fun onResponse(
//                    call: Call<ScheduleResponse>,
//                    response: Response<ScheduleResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        list = response.body()?.scheduleList
//                    } else {
//                        Toast.makeText(
//                            activity,
//                            activity.getString(R.string.request_error_message),
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<ScheduleResponse>, t: Throwable) {
//                    Toast.makeText(
//                        activity,
//                        activity.getString(R.string.server_error_message),
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//
//            })
//        return list
//    }

}
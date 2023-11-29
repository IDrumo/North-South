package com.project.north_south.viewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.north_south.R
import com.project.north_south.databinding.FragmentTripBinding
import models.TripItem
import java.lang.reflect.Type

class TripFragmentViewModel(val context: Application): AndroidViewModel(context) {
    private val sharedTrip: SharedPreferences = context.getSharedPreferences("trip_info", Context.MODE_PRIVATE)
    private val sharedTripStarted: SharedPreferences = context.getSharedPreferences("trip_started_info", Context.MODE_PRIVATE)
    private val sharedTripTime: SharedPreferences = context.getSharedPreferences("trip_time_info", Context.MODE_PRIVATE)
    private lateinit var tripBinding: FragmentTripBinding
    fun initFields(binding: FragmentTripBinding){
        tripBinding = binding
        val json = sharedTrip.getString("trip", null)
        val gson = Gson()
        val data = gson.fromJson(json, TripItem::class.java)


        if (data != null) {
            binding.apply {
                timeStart.text = data.time_start
                timeFinish.text = data.time_finish
                stationStart.text = data.stations[data.station_index++].name
                stationFinish.text = data.stations[data.station_index].name
            }
        }
    }

    fun nextStep(stopwatchViewModel: StopwatchViewModel) {
        if (sharedTripStarted.getBoolean("trip_started", false)) {
            val gson = Gson()

            var json = sharedTrip.getString("trip", null)
            val tripData = gson.fromJson(json, TripItem::class.java)
            tripBinding.stationStart.text = tripData.stations[tripData.station_index++].name
            tripBinding.stationFinish.text = tripData.stations[tripData.station_index].name
            val index = tripData.station_index
            json = gson.toJson(tripData)
            sharedTrip.edit().putString("trip", json).apply()
//            sharedTripStarted.edit().putBoolean("trip_started", true).putInt("station_index", sharedTripStarted.getInt("station_index", 0) + 1).apply()


            val time = stopwatchViewModel.getCurrentTimeString()
            json = sharedTripTime.getString("data", null)
            val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
            val timeData = gson.fromJson<ArrayList<String>>(json, type) ?: arrayListOf()
            timeData[index] = time
            sharedTripTime.edit().putString("data", gson.toJson(tripData)).apply()
        }
    }

    fun previousStep(stopwatchViewModel: StopwatchViewModel) {
        if (sharedTripStarted.getBoolean("trip_started", false)) {
            val time = stopwatchViewModel.getCurrentTimeString()
            var json = sharedTrip.getString("trip", null)
            val gson = Gson()
            val data = gson.fromJson(json, TripItem::class.java)
            tripBinding.stationStart.text = data.stations[data.station_index-2].name
            tripBinding.stationFinish.text = data.stations[--data.station_index].name
            json = gson.toJson(data)
            sharedTrip.edit().putString("trip", json).apply()
//            sharedTripStarted.edit().putBoolean("trip_started", true).putInt("station_index", sharedTripStarted.getInt("station_index", 0) + 1).apply()
        }
    }

    fun startStop(stopwatchViewModel: StopwatchViewModel) {
        if (sharedTripStarted.getBoolean("trip_started", false)){
            sharedTripStarted.edit().putBoolean("trip_started", true).apply()
            stopwatchViewModel.start()
            tripBinding.startStopButton.setBackgroundColor(context.getColor(R.color.dust_red))
            tripBinding.startStopButton.text = context.getString(R.string.end_trip)
        }else{
            sharedTripStarted.edit().putBoolean("trip_started", false).apply()
            stopwatchViewModel.stop()
            tripBinding.startStopButton.setBackgroundColor(context.getColor(R.color.dust_green))
            tripBinding.startStopButton.text = context.getString(R.string.start_trip)
        }
    }

    fun clearShare(){
        sharedTrip.edit().putBoolean("sub_frame_part_active", false).apply()
        sharedTripStarted.edit().clear().apply()
        sharedTripTime.edit().clear().apply()
    }
}
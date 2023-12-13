package com.project.north_south.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.project.north_south.R
import com.project.north_south.databinding.FragmentTripBinding
import com.project.north_south.subAlgorithms.ErrorMessage
import com.project.north_south.subAlgorithms.Storage
import network.InitAPI

class TripFragmentViewModel(val context: Application) : AndroidViewModel(context) {

    private val storage = Storage(context)
    private lateinit var tripBinding: FragmentTripBinding
    fun initFields(binding: FragmentTripBinding) {
        tripBinding = binding
        val data = storage.getTrip()

        binding.apply {
            timeStart.text = data.time_start
            timeFinish.text = data.time_finish
            stationStart.text = data.stations[data.station_index++]
            stationFinish.text = data.stations[data.station_index]
        }

        if (storage.tripStarted()) {
            tripBinding.startStopButton.setBackgroundColor(context.getColor(R.color.dust_red))
            tripBinding.startStopButton.text = context.getString(R.string.end_trip)
        } else {
            tripBinding.startStopButton.setBackgroundColor(context.getColor(R.color.dust_green))
            tripBinding.startStopButton.text = context.getString(R.string.start_trip)
        }

    }

    fun nextStep(stopwatchViewModel: StopwatchViewModel) {
        if (storage.tripStarted()) {

            val (index, firstValue, secondValue) = storage.nextStation()
            tripBinding.stationStart.text = firstValue
            tripBinding.stationFinish.text = secondValue


            val time = stopwatchViewModel.getCurrentTimeLong()
            storage.addTime(index, time)
        }
    }

    fun previousStep(stopwatchViewModel: StopwatchViewModel) {
        if (storage.tripStarted()) {
            val time = stopwatchViewModel.getCurrentTimeLong()
            val (index, firstValue, secondValue) = storage.previousStation()
            tripBinding.stationStart.text = firstValue
            tripBinding.stationFinish.text = secondValue
        }
    }

    fun startStop(stopwatchViewModel: StopwatchViewModel) {
        if (storage.tripStarted()) {
            storage.switchTripState()
            storage.addTime((storage.getTrip().station_index), stopwatchViewModel.getCurrentTimeLong())
            stopwatchViewModel.stop()
            shareInfo()
            tripBinding.startStopButton.setBackgroundColor(context.getColor(R.color.dust_green))
            tripBinding.startStopButton.text = context.getString(R.string.start_trip)
        } else {
            storage.switchTripState()
            stopwatchViewModel.start()
            tripBinding.startStopButton.setBackgroundColor(context.getColor(R.color.dust_red))
            tripBinding.startStopButton.text = context.getString(R.string.end_trip)
        }
    }

    fun shareInfo() {
        val error = ErrorMessage(context)
        InitAPI().sendData(context, object : InitAPI.SaveDataCallback{
            override fun onSuccess() {
                error.save_success()
            }

            override fun onError(e: String) {
                error.custom_error(context.getString(R.string.flight_error))
//                Log.d("MyLog", "Ошибка с данными")
            }

            override fun onFailure(e: Throwable) {
                error.connection_error()
//                Log.d("MyLog", "Ошибка с На сервере")
            }
        })
        storage.clearTripData()
    }

    fun clearShare() {
        storage.clearTripInfo()
    }
}
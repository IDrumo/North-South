package com.project.north_south.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.project.north_south.R
import com.project.north_south.databinding.FragmentTripBinding
import com.project.north_south.subAlgorithms.Storage

class TripFragmentViewModel(val context: Application): AndroidViewModel(context) {

    private val storage = Storage(context)
    private lateinit var tripBinding: FragmentTripBinding
    fun initFields(binding: FragmentTripBinding){
        tripBinding = binding
        val data = storage.getTrip()

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
        if (storage.tripStarted()) {

            val (index, firstValue, secondValue) = storage.nextStation()
            tripBinding.stationStart.text = firstValue
            tripBinding.stationFinish.text = secondValue


            val time = stopwatchViewModel.getCurrentTimeString()
            storage.addTime(index, time)
        }
    }

    fun previousStep(stopwatchViewModel: StopwatchViewModel) {
        if (storage.tripStarted()) {
            val time = stopwatchViewModel.getCurrentTimeString()
            val (index, firstValue, secondValue) = storage.previousStation()
            tripBinding.stationStart.text = firstValue
            tripBinding.stationFinish.text = secondValue
        }
    }

    fun startStop(stopwatchViewModel: StopwatchViewModel) {
        if (storage.tripStarted()){
            storage.switchTripState()
            stopwatchViewModel.stop()
            shareInfo()
            tripBinding.startStopButton.setBackgroundColor(context.getColor(R.color.dust_green))
            tripBinding.startStopButton.text = context.getString(R.string.start_trip)
        }else{
            storage.switchTripState()
            stopwatchViewModel.start()
            tripBinding.startStopButton.setBackgroundColor(context.getColor(R.color.dust_red))
            tripBinding.startStopButton.text = context.getString(R.string.end_trip)
        }
    }

    fun shareInfo(){
        // отправка
        val times = storage.getTimeResult()
        storage.clearTripTime()
        val passengers = storage.getPassengers()
        storage.clearPassengers()
    }

    fun clearShare(){
        storage.clearTripInfo()
    }
}
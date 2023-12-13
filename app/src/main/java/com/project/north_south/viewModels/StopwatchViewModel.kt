package com.project.north_south.viewModels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StopwatchViewModel : ViewModel() {
    private val _time = MutableLiveData<Long>()
    val time: LiveData<Long>
        get() = _time

    private var startTime: Long = 0
    private var isRunning = false
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - startTime
                _time.value = elapsedTime
                handler.postDelayed(this, 10) // Обновляем каждые 10 миллисекунд
            }
        }
    }

    fun start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis()
            isRunning = true
            handler.post(runnable)
        }
    }

    fun getCurrentTimeString(): String {
        val timeInMillis = time.value ?: 0
        val hours = timeInMillis / 1000 / 60 / 60
        val minutes = (timeInMillis / 1000 / 60) % 60
        val seconds = timeInMillis / 1000 % 60
        val milliseconds = timeInMillis % 1000
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun getCurrentTimeLong(): Long {
        if (time.value == null)
            return 0
        return time.value!!
    }

    fun stop() {
        if (isRunning) {
            isRunning = false
            handler.removeCallbacks(runnable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stop()
    }
}
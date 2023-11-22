package com.project.north_south.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.north_south.R
import com.project.north_south.databinding.ActivityTripPageBinding
import models.TripItem

class TripPage : AppCompatActivity() {
    lateinit var binding: ActivityTripPageBinding
    var checker : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent?.getSerializableExtra("data") as? TripItem
        initFields(data)
    }

    fun initFields(data: TripItem?) = with(binding){
        timeStart.text = data?.time_start
        timeFinish.text = data?.time_finish
        stationStart.text = data?.stations?.get(0)?.name
        stationFinish.text = data?.stations?.get(data.stations.size - 1)?.name
        nextStep.setOnClickListener{

        }
        priviousStep.setOnClickListener{

        }
        startStopButton.setOnClickListener{
            when(checker){
                true -> {
                    // запустить таймер
                }
                false -> {
                    // остановить таймер и отправить данные
//                    setResult(RESULT_OK, resultIntent)
//                    finish()
                }
            }
            checker = checker.not()
        }
    }
}
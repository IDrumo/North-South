package com.project.north_south.viewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.north_south.R
import com.project.north_south.databinding.FragmentScannerBinding
import com.project.north_south.fragments.TicketFragment
import com.project.north_south.subAlgorithms.calculateSHA256
import com.project.north_south.subAlgorithms.doAnimate
import models.Ticket
import java.lang.reflect.Type


class ScannerFragmentViewModel(context: Application): AndroidViewModel(context) {
    private var ticketFragment: TicketFragment? = null
    val ticketDate: MutableLiveData<Int> = MutableLiveData()
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("passenger_control", Context.MODE_PRIVATE)

    fun openSupport(binding: FragmentScannerBinding, fragmentManager: FragmentManager){
        if (ticketFragment == null) {
            ticketFragment = TicketFragment.newInstance()
            fragmentManager.beginTransaction()
                .add(R.id.supportFragmentContainer, ticketFragment!!)
                .commit()
        } else {
            fragmentManager.beginTransaction()
                .remove(ticketFragment!!)
                .commit()
            ticketFragment = null
        }
    }

    fun savePassengerInfo(index: Int) {
        // Чтение списка из SharedPreferences
        val gson = Gson()
        val jsonList = sharedPreferences.getString("passenger_control", "")
        val type: Type = object : TypeToken<ArrayList<Boolean?>?>() {}.type
        val savedList = gson.fromJson<ArrayList<Boolean>>(jsonList, type)

        // Изменение списка
        savedList[index] = true

        // Сохранение списка в SharedPreferences
        val editor = sharedPreferences.edit()
        val json = gson.toJson(savedList)
        editor.putString("passenger_control", json)
        editor.apply()
    }

    fun checkQR(data: String?, view: View){

        val gson = Gson()
        val ticket = gson.fromJson(data, Ticket::class.java)

        if (calculateSHA256("${ticket.flight_number}${ticket.seat_number}") == ticket.code_number && data.isNullOrEmpty()){
            doAnimate(view, R.id.success_anim)
            ticketDate.value = ticket.seat_number
        }else{
            doAnimate(view, R.id.cancel_anim)
            val snackbar = Snackbar.make(view, "Это билет на рейс ${ticket.flight_number} в ${ticket.time_start}", 7000)
            val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            snackbar.show()
        }
    }


}
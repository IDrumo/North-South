package com.project.north_south.ViewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.north_south.R
import com.project.north_south.databinding.FragmentScannerBinding
import com.project.north_south.fragments.TicketFragment
import java.lang.reflect.Type
import java.security.MessageDigest


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
        val param0 = "date"
        val param1 = "flight_id"
        val param2 = "place_id"
        val param3 = "code"
        val param0_start = data?.lastIndexOf(param0) as Int
        val param1_start = data?.lastIndexOf(param1) as Int
        val param2_start = data?.lastIndexOf(param2) as Int
        val param3_start = data?.lastIndexOf(param3) as Int
        var param_value_0 = ""
        var param_value_1 = ""
        var param_value_2 = ""
        var param_value_3 = ""
        if (param1_start != -1) param_value_0 = data.substring(param0_start+param0.length-1, param1_start - (param0_start+param0.length-1)).trim()
        if (param1_start != -1) param_value_1 = data.substring(param1_start+param1.length-1, param2_start - (param1_start+param1.length-1)).trim()
        if (param2_start != -1) param_value_2 = data.substring(param2_start+param2.length-1, param3_start - (param2_start+param2.length-1)).trim()
        if (param3_start != -1) param_value_3 = data.substring(param3_start+param3.length-1, data.length - (param3_start+param3.length-1)).trim()

        if (calculateSHA256("${param_value_1}${param_value_2}") == param_value_3 && data.isNullOrEmpty()){
            doAnimate(view, R.id.success_anim)
        }else{
            doAnimate(view, R.id.cancel_anim)
            val snackbar = Snackbar.make(view, "Это билет на рейс ${param_value_1} в ${param_value_0}", 7000)
            val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            snackbar.show()
        }
    }

    fun calculateSHA256(text: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val bytes = messageDigest.digest(text.toByteArray())

        // Преобразование байтового массива в строку шестнадцатеричного представления
        val stringBuilder = StringBuilder()
        for (byte in bytes) {
            stringBuilder.append(String.format("%02x", byte))
        }

        return stringBuilder.toString()
    }

    private fun doAnimate(view: View, id: Int){
        val anim = view.findViewById<LottieAnimationView>(id)
        anim?.speed = 2f
        anim?.repeatMode = LottieDrawable.REVERSE
        anim?.repeatCount = 1
        anim?.playAnimation()
    }
}
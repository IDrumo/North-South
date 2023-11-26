package com.project.north_south.ViewModels

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import com.project.north_south.R
import com.project.north_south.databinding.FragmentTicketBinding
import java.security.MessageDigest
import kotlin.text.toIntOrNull

class TicketFragmentViewModel: ViewModel() {
    val ticketDate: MutableLiveData<Int> = MutableLiveData()

    fun check(view: View, binding: FragmentTicketBinding){
        val flight_number = binding.flightNumber.text.toString()
        val place_number = binding.placeNumber.text.toString()
        val code_number = binding.codeNumber.text.toString()

        if (code_number.equals(calculateSHA256("${flight_number}${place_number}").substring(0,6))){
            doAnimate(view.rootView, R.id.success_anim)
            ticketDate.value = place_number.toInt()
        }else{
            doAnimate(view.rootView, R.id.cancel_anim)
            val snackbar = Snackbar.make(view, "Это билет на рейс ${flight_number}", 7000)
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
package com.project.north_south.viewModels

import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.project.north_south.R
import com.project.north_south.databinding.FragmentTicketBinding
import com.project.north_south.subAlgorithms.calculateSHA256
import com.project.north_south.subAlgorithms.doAnimate
import java.util.Locale

class TicketFragmentViewModel: ViewModel() {
    val ticketDate: MutableLiveData<Long> = MutableLiveData()

    fun check(view: View, binding: FragmentTicketBinding){
        val tiket_id = binding.ticketNumber.text.toString()
        val flight_number = binding.flightNumber.text.toString()
        val place_number = binding.placeNumber.text.toString()
        val code_number = binding.codeNumber.text.toString().lowercase(Locale.ROOT)

        if (code_number == calculateSHA256("${tiket_id} ${flight_number} ${place_number}").substring(0,6)){
            doAnimate(view.rootView, R.id.success_anim)
            ticketDate.value = tiket_id.toLong()
        }else{
            doAnimate(view.rootView, R.id.cancel_anim)
            val snackbar = Snackbar.make(view, "Это билет на рейс ${flight_number}", 7000)
            val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            snackbar.show()
        }
    }
}
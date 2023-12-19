package com.project.north_south.viewModels

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.north_south.R
import com.project.north_south.databinding.FragmentTicketBinding
import com.project.north_south.subAlgorithms.Snack
import com.project.north_south.subAlgorithms.Storage
import com.project.north_south.subAlgorithms.calculateSHA256
import com.project.north_south.subAlgorithms.doAnimate
import java.util.Locale

class TicketFragmentViewModel: ViewModel() {
    val ticketDate: MutableLiveData<Long> = MutableLiveData()

    fun check(context: Context, view: View, binding: FragmentTicketBinding){
        val storage = Storage(context)
        val snack = Snack(context, view.rootView)

        val ticket_id = binding.ticketNumber.text.toString()
        val flight_number = binding.flightNumber.text.toString()
        val place_number = binding.placeNumber.text.toString()
        val code_number = binding.codeNumber.text.toString().lowercase(Locale.ROOT)

        if (storage.getTrip().id.toInt() == 0){
            snack.no_flight_selected()
            return
        }

        if (code_number == calculateSHA256("${ticket_id} ${flight_number} ${place_number}").substring(0,6)){
            if (flight_number.toLong() == storage.getTrip().id){
                if (ticket_id.toLong() in storage.getPassengers()){
                    snack.ticket_already_used()
                    return
                }
                ticketDate.value = ticket_id.toLong()
                return
            }
            snack.an_outdated_ticket(flight_number.toLong(), "", storage.getTrip().id)
            return

        }else{
            snack.the_ticket_is_invalid()
        }
    }
}
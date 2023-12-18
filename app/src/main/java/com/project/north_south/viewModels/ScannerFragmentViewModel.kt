package com.project.north_south.viewModels

import android.app.Application
import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.project.north_south.R
import com.project.north_south.fragments.TicketFragment
import com.project.north_south.subAlgorithms.Snack
import com.project.north_south.subAlgorithms.Storage
import com.project.north_south.subAlgorithms.calculateSHA256
import models.Ticket
import java.util.Locale


class ScannerFragmentViewModel(context: Application): AndroidViewModel(context) {
    private var ticketFragment: TicketFragment? = null
    val ticketDate: MutableLiveData<Long> = MutableLiveData()
    private lateinit var storage: Storage
    private lateinit var snack: Snack

    fun init(context: Context, view: View){
        storage = Storage(context)
        snack = Snack(context, view)
    }
    fun openSupport(fragmentManager: FragmentManager){
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

    fun savePassenger(ticketId: Long) {
        storage.savePassengerInfo(ticketId)
    }

    fun checkQR(data: String?){
        try {
            val gson = Gson()
            val ticket = gson.fromJson(data, Ticket::class.java)

            if (storage.getTrip().id.toInt() == 0) {
                snack.no_flight_selected()
                return
            }

            if (calculateSHA256("${ticket.ticket_id} ${ticket.flight_number} ${ticket.seat_number}") ==
                ticket.code_number.lowercase(Locale.ROOT) && !data.isNullOrEmpty()
            ) {
                if (ticket.flight_number == storage.getTrip().id) {
                    ticketDate.value = ticket.ticket_id
                    return
                }
                snack.an_outdated_ticket(
                    ticket.flight_number,
                    ticket.time_start,
                    storage.getTrip().id
                )
                return
            }
            snack.the_ticket_is_invalid()
            return
        }catch (e: JsonSyntaxException){
            return
        }
    }


}
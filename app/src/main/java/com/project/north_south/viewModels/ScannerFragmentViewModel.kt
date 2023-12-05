package com.project.north_south.viewModels

import android.app.Application
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.project.north_south.R
import com.project.north_south.fragments.TicketFragment
import com.project.north_south.subAlgorithms.Storage
import com.project.north_south.subAlgorithms.calculateSHA256
import com.project.north_south.subAlgorithms.doAnimate
import models.Ticket
import java.util.Locale


class ScannerFragmentViewModel(context: Application): AndroidViewModel(context) {
    private var ticketFragment: TicketFragment? = null
    val ticketDate: MutableLiveData<Long> = MutableLiveData()
    private val storage = Storage(context)

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

    fun checkQR(data: String?, view: View){

        val gson = Gson()
        val ticket = gson.fromJson(data, Ticket::class.java)

        if (calculateSHA256("${ticket.ticket_id} ${ticket.flight_number} ${ticket.seat_number}") ==
            ticket.code_number.lowercase(Locale.ROOT) && !data.isNullOrEmpty()){
            doAnimate(view, R.id.success_anim)
            ticketDate.value = ticket.ticket_id
        }else{
            doAnimate(view, R.id.cancel_anim)
            val snackbar = Snackbar.make(view, "Это билет на рейс ${ticket.flight_number} в ${ticket.time_start}", 7000)
            val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            snackbar.show()
        }
    }


}
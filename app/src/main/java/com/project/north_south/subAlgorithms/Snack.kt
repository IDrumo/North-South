package com.project.north_south.subAlgorithms

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.project.north_south.R

class Snack (
    val context: Context,
    val view: View
){

    fun no_flight_selected(){
        doAnimate(view, R.id.cancel_anim)
        val snackbar = Snackbar.make(view, context.getString(R.string.ticket_error_message_1), 7000)
        val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        snackbar.show()
    }

    fun an_outdated_ticket(trip_number: Long, time_start: String, current_trip_number: Long){
        doAnimate(view, R.id.cancel_anim)
        val snackbar = Snackbar.make(view, context.getString(R.string.ticket_error_message_2_1) +
                trip_number.toString() + context.getString(R.string.ticket_error_message_2_2) +
                time_start + context.getString(R.string.ticket_error_message_2_3)+
                current_trip_number, 7000)
        val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
        snackbar.show()
    }

    fun the_ticket_is_invalid(){
        doAnimate(view, R.id.cancel_anim)
        val snackbar = Snackbar.make(view, context.getString(R.string.ticket_error_message_3), 7000)
        val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        snackbar.show()
    }
}
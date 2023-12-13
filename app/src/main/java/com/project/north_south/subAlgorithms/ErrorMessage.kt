package com.project.north_south.subAlgorithms

import android.content.Context
import android.widget.Toast
import com.project.north_south.R

class ErrorMessage(val context: Context) {

    fun custom_error(string: String){
        Toast.makeText(
            context,
            string,
            Toast.LENGTH_LONG
        ).show()
    }
    fun connection_error() {
        Toast.makeText(
            context,
            context.getString(R.string.conection_error_message),
            Toast.LENGTH_LONG
        ).show()
    }

    fun not_found_error() {
        Toast.makeText(
            context,
            context.getString(R.string.not_found_error_message),
            Toast.LENGTH_LONG
        ).show()
    }

    fun empty_fields_error() {
        Toast.makeText(
            context,
            R.string.toast_error_message,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun save_success(){
        Toast.makeText(
            context,
            R.string.save_success_message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
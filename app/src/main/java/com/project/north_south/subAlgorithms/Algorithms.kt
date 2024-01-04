package com.project.north_south.subAlgorithms

import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Calendar

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

fun doAnimate(view: View, id: Int){
    val anim = view.findViewById<LottieAnimationView>(id)
    anim?.speed = 2f
    anim?.repeatMode = LottieDrawable.REVERSE
    anim?.repeatCount = 1
    anim?.playAnimation()
}

fun getToday(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month =
        calendar.get(Calendar.MONTH) + 1 // добавляем 1, так как месяцы в Calendar начинаются с 0
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return "$year-${String.format("%02d", month)}-${String.format("%02d", day)}"
}

fun getFinishTime(): String {
    val calendar: Calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("HH:mm:ss")
    return  dateFormat.format(calendar.getTime())
}
package com.project.north_south.subAlgorithms

import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import java.security.MessageDigest

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
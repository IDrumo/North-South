package models

import android.content.Intent

class FullUserInfo (intent: Intent) {
    val login = intent.getStringExtra("login")
    val password = intent.getStringExtra("password")
    val role = intent.getStringExtra("role")
    val token = intent.getStringExtra("token")
}
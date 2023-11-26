package models

import android.content.Intent

class FullUserInfo {
    var login: String? = null
    var password: String? = null
    var role: String? = null
    var token: String? = null
    var first_name: String? = null
    var last_name: String? = null
    var patronymic: String? = null
    var bus_code: String? = null

    constructor()

    constructor(intent: Intent) {
        login = intent.getStringExtra("login")
        password = intent.getStringExtra("password")
        role = intent.getStringExtra("role")
        token = intent.getStringExtra("token")
        first_name = intent.getStringExtra("first_name")
        last_name = intent.getStringExtra("last_name")
    }
    constructor(new_first_name : String, new_last_name : String){
        first_name = new_first_name
        last_name = new_last_name
    }
}
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

    constructor(login : String, password : String, user : UserLoginResponse){
        this.login = login
        this.password = password
        role = user.role
        token = user.token
        first_name = user.first_name
        last_name = user.last_name
        patronymic = user.patronymic
        bus_code = user.bus_code
    }

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

    constructor(
        login: String,
        password: String,
        role: String,
        token: String,
        first_name: String,
        last_name: String,
        patronymic: String,
        bus_code: String
    ) {
        this.login = login
        this.password = password
        this.role = role
        this.token = token
        this.first_name = first_name
        this.last_name = last_name
        this.patronymic = patronymic
        this.bus_code = bus_code
    }
}
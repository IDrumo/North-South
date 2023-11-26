package models

data class UserLoginResponse(
    val role: String,
    val token: String,
    val first_name: String,
    val last_name: String,
    val patronymic: String,
    val bus_code: String
)
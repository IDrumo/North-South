package models

data class UserLoginRequest(
    val login: String,
    val password: String,
    val date: String = "2022-01-01"
)
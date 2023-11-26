package models

class ApiResponse (
    val code: Int,
    val message: String,
    val data: UserLoginResponse
)
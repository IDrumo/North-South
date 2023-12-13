package models

data class UserLoginResponse(
    val first_name: String,
    val last_name: String,
    val patronymic: String,
    val bus_code: Long,
    val token: String,
    val daily_schedule: ArrayList<ScheduleResponse>
)
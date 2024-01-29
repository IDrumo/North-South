package models

data class GetScheduleResponse(
    val first_name: String,
    val last_name: String,
    val patronymic: String,
    val bus_code: Long,
    val token: String,
    // все выше должно быть перенесено в UserLoginResponse
    val daily_schedule: ArrayList<ScheduleResponse>
)
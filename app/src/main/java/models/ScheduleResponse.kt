package models

data class ScheduleResponse(
    val id : Long,
    val date: Long,
    val status: String,
    val tickets: ArrayList<TicketResponse>,
    val trip: Trip
)
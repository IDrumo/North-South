package models

class Ticket (
    val ticket_id: Long,
    val flight_number: Long,
    val time_start: String,
    val seat_number: Int,
    val code_number: String
)
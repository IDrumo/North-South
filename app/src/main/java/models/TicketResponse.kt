package models

class TicketResponse (
    val id: Long,
    val departure_id: Long,
    val bus_route_id: Long,
    val place_number: Long,
    val trip_id: Long,
    val date: Long,
    val time: String,
    val departure_point: String,
    val place_of_arrival: String,
    val is_visited: Boolean,
    val first_name: String,
    val last_name: String,
    val surname: String
)
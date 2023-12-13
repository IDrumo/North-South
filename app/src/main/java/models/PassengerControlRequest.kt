package models

class PassengerControlRequest (
    val route_id: Long,
    val tickets_id: ArrayList<Long>
)
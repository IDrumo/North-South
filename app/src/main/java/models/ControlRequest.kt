package models

class ControlRequest (
    val departure_id: Long,
    val arrive_time: String,
    val timeslots: ArrayList<Long>,
    val tickets_id: ArrayList<Long>,
    val status: String = "done",
)
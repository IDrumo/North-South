package models

import java.sql.Time
import java.time.LocalDateTime

data class TripItem(
    var id: Long,
    var time_start: String,
    var time_finish: String,
    var stations: ArrayList<Station>,
    var bus: Bus
)

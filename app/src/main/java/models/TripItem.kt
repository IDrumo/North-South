package models

import java.io.Serializable
import java.sql.Time
import java.time.LocalDateTime

data class TripItem(
    var id: Long,
    var time_start: String,
    var time_finish: String,
    var stations: ArrayList<Station>,
    var station_index: Int = -1,
    var bus: Bus
) : Serializable

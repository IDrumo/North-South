package models

import java.io.Serializable
import java.sql.Time
import java.time.LocalDateTime

data class TripItem(
    var id: Long = 0,
    var time_start: String = "",
    var time_finish: String = "",
    var stations: ArrayList<String> = ArrayList(),
    var place_number: Long = 0,
    var station_index: Int = 0
) : Serializable


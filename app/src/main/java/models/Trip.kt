package models

class Trip (
    val id: Long,
    val departure_time: String,
    val days: ArrayList<Int>,
    val driver: Driver,
    val bus: Bus,
    val stations: ArrayList<String>
)
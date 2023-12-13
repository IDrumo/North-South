package models

data class Bus(
    var id: Long,
    var driver_id: Long,
    var model: String,
    var code: String,
    var status: String,
    var number_of_sits: Long,
)

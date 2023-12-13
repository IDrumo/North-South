package com.project.north_south.subAlgorithms

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import models.FullUserInfo
import models.ScheduleResponse
import models.TripItem
import models.UserLoginResponse
import java.lang.reflect.Type

class Storage(context: Context) {
    private val passengers: SharedPreferences = context.getSharedPreferences("passenger_control", Context.MODE_PRIVATE)
    private val userData: SharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    private val trip: SharedPreferences = context.getSharedPreferences("trip_info", Context.MODE_PRIVATE)
    private val tripTime: SharedPreferences = context.getSharedPreferences("trip_time_info", Context.MODE_PRIVATE)
    private val tripStarted: SharedPreferences = context.getSharedPreferences("trip_started_info", Context.MODE_PRIVATE)
    private val fullRoadmap: SharedPreferences = context.getSharedPreferences("roadmap_info", Context.MODE_PRIVATE)
    private val roadmap: SharedPreferences = context.getSharedPreferences("roadmap", Context.MODE_PRIVATE)
    private val schedule: SharedPreferences = context.getSharedPreferences("schedule", Context.MODE_PRIVATE)

    fun clearAll(){
        passengers.edit().clear().apply()
        userData.edit().clear().apply()
        trip.edit().clear().apply()
        tripTime.edit().clear().apply()
        tripStarted.edit().clear().apply()
        fullRoadmap.edit().clear().apply()
        roadmap.edit().clear().apply()
    }

    fun clearTripData(){
        tripStarted.edit().clear().apply()
        tripTime.edit().clear().apply()
        passengers.edit().clear().apply()
    }
    fun clearTripInfo(){
        trip.edit().clear().apply()
        tripStarted.edit().clear().apply()
        tripTime.edit().clear().apply()
        passengers.edit().clear().apply()
    }

    fun clearCurrentData(){
        clearTripInfo()
        roadmap.edit().clear().apply()
        fullRoadmap.edit().clear().apply()
    }

    fun clearUserData(){
        userData.edit().clear().apply()
    }

    fun setTrip(trip: TripItem){
        val gson = Gson()
        val json = gson.toJson(trip)
        this.trip.edit()
            .putString("current_trip", json)
            .putBoolean("sub_frame_part_active", true)
            .apply()
    }

    fun getTripSelectedStatus(): Boolean {
        return trip.getBoolean("sub_frame_part_active", false)
    }

    fun getNumberOfPlaces(): Long{
        val json = trip.getString("current_trip", null)
        val gson = Gson()
        val trip = if (json != null)
            gson.fromJson(json, TripItem::class.java)
        else
            TripItem()
        return trip.place_number
    }

    fun getTrip(): TripItem {
        val json = trip.getString("current_trip", null)
        val gson = Gson()
        if (json != null)
            return gson.fromJson(json, TripItem::class.java)
        return TripItem()
    }

    fun tripStarted(): Boolean {
        return tripStarted.getBoolean("trip_started", false)
    }

    fun switchTripState(){
        if (tripStarted()) tripStarted.edit().putBoolean("trip_started", false).apply()
        else tripStarted.edit().putBoolean("trip_started", true).apply()
    }

    fun addTime(index:Int, time:Long){
        val gson = Gson()
        val json = tripTime.getString("data", null)
        val type: Type = object : TypeToken<ArrayList<Long?>?>() {}.type
        var timeData = gson.fromJson<ArrayList<Long>>(json, type)
        if (timeData.isNullOrEmpty()) {
            timeData = ArrayList<Long>(getStationNumber() - 1).apply {
                repeat(getStationNumber() - 1) {
                    add(0)
                }
            }
        }
        timeData[index] = time
        tripTime.edit().putString("data", gson.toJson(timeData)).apply()
    }

    fun rollBackTime(){

    }

    fun getTimeResult(): ArrayList<Long> {
        val gson = Gson()
        val json = tripTime.getString("data", null)
        val type: Type = object : TypeToken<ArrayList<Long?>?>() {}.type
        val timeData = gson.fromJson<ArrayList<Long>>(json, type) ?: arrayListOf()
        return timeData
    }

    fun getStationNumber(): Int {
        val gson = Gson()
        val json = trip.getString("current_trip", null)
        val tripData = gson.fromJson(json, TripItem::class.java)
        return tripData.stations.size
    }
    fun nextStation(): Triple<Int, String, String> {
        val gson = Gson()
        var json = trip.getString("current_trip", null)
        val tripData = gson.fromJson(json, TripItem::class.java)

        if (tripData.station_index < tripData.stations.size - 2) {
            val index = ++tripData.station_index

            val firstValue = tripData.stations[tripData.station_index]
            val secondValue = tripData.stations[tripData.station_index + 1]

            json = gson.toJson(tripData)
            trip.edit().putString("current_trip", json).apply()
            return Triple(index, firstValue, secondValue)
        }else{
            val index = tripData.station_index

            val firstValue = tripData.stations[tripData.station_index]
            val secondValue = tripData.stations[tripData.station_index + 1]

            json = gson.toJson(tripData)
            trip.edit().putString("current_trip", json).apply()
            return Triple(index, firstValue, secondValue)
        }
    }

    fun previousStation(): Triple<Int, String, String> {
        var json = trip.getString("current_trip", null)
        val gson = Gson()
        val data = gson.fromJson(json, TripItem::class.java)

        if (data.station_index > 0) {
            val index = --data.station_index
            val firstValue = data.stations[data.station_index]
            val secondValue = data.stations[data.station_index + 1]
            json = gson.toJson(data)
            trip.edit().putString("current_trip", json).apply()

            return Triple(index, firstValue, secondValue)
        }else{
            val index = data.station_index
            val firstValue = data.stations[data.station_index]
            val secondValue = data.stations[data.station_index + 1]
            json = gson.toJson(data)
            trip.edit().putString("current_trip", json).apply()

            return Triple(index, firstValue, secondValue)
        }
    }
    fun saveUserInfo(login : String, password : String, user: UserLoginResponse?){
        userData.edit()
            .putBoolean("active", true)
            .putString("login", login)
            .putString("password", password)
            .putString("token", user?.token)
            .putString("first_name", user?.first_name)
            .putString("last_name", user?.last_name)
            .putString("patronymic", user?.patronymic)
            .putString("bus_code", user?.bus_code.toString())
            .apply()

            if (user != null) {
                this.saveRoadmap(user.daily_schedule)
            }
    }

    fun getUser(): FullUserInfo {
        return FullUserInfo(
            userData.getString("login", "")!!,
            userData.getString("password", "")!!,
            userData.getString("token", "")!!,
            userData.getString("first_name", "")!!,
            userData.getString("last_name", "")!!,
            userData.getString("patronymic", "")!!,
            userData.getString("bus_code", "")!!,
        )
    }

    fun getUserLoginPassword(): Pair<String?, String?> {
        val login = userData.getString("login", null)
        val password = userData.getString("password", null)
        return Pair(login, password)
    }

    fun getUserStatus(): Boolean {
        return userData.getBoolean("active", false)
    }
    fun savePassengerInfo(ticket: Long) {
        // Чтение списка из SharedPreferences
        val savedList = getPassengers()

        // Изменение списка

        if (!savedList.contains(ticket)) savedList.add(ticket)
//        Log.d("MyLog", "Место $index было помечено как занятое")

        // Сохранение списка в SharedPreferences
        val gson = Gson()
        val editor = passengers.edit()
        val json = gson.toJson(savedList)
        editor.putString("passenger_control", json)
        editor.apply()
    }

    fun getPassengers(): ArrayList<Long> {
        val gson = Gson()
        val jsonList = passengers.getString("passenger_control", "")
        val savedList: ArrayList<Long> = if (jsonList.isNullOrEmpty()) {
            arrayListOf<Long>()
        } else {
            val type: Type = object : TypeToken<ArrayList<Long>>() {}.type
            gson.fromJson(jsonList, type)
        }
        return savedList
    }

    fun saveRoadmap(schedules: ArrayList<ScheduleResponse>){
        val gson = Gson()
        var json = gson.toJson(schedules)
        fullRoadmap.edit().putString("full_roadmap", json).apply()

        val trips = ArrayList<TripItem>()
        schedules.forEach{schedule ->
            trips.add(
                TripItem(
                schedule.id,
                schedule.trip.departure_time,
                "00:00",
                schedule.trip.stations,
                schedule.trip.bus.number_of_sits)
            )
        }
        json = gson.toJson(trips)
        roadmap.edit().putString("roadmap", json).apply()
    }

    fun getRoadmapInfo(): ArrayList<ScheduleResponse>{
        val gson = Gson()
        val jsonList = fullRoadmap.getString("full_roadmap", "")
        val type: Type = object : TypeToken<ArrayList<ScheduleResponse?>?>() {}.type
        return gson.fromJson(jsonList, type)
    }

    fun saveSchedule(){

    }
    fun getSchedule(){

    }

    fun getRoadmap(): ArrayList<TripItem>? {
        val gson = Gson()
        val jsonList = roadmap.getString("roadmap", "")
        val type: Type = object : TypeToken<ArrayList<TripItem?>?>() {}.type
        return gson.fromJson(jsonList, type)
    }

    fun getControl(): Pair<ArrayList<Long>, ArrayList<Long>>{
        val tickets = getPassengers()
        val time = getTimeResult()
        return Pair(tickets, time)
    }
}
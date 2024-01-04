package network

import models.ApiResponse
import models.ControlRequest
import models.PassengerControlRequest
import models.ScheduleResponse
import models.TimeControlRequest
import models.UserLoginRequest
import models.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    fun loginUser(@Body user: UserLoginRequest): Call<UserLoginResponse>

    @POST("Control")
    fun passengerControl(@Body data: ControlRequest): Call<Void>

    @POST("login")
    fun refreshToken(): Call<UserLoginResponse>

}
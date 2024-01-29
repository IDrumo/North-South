package network

import models.ControlRequest
import models.UserLoginRequest
import models.GetScheduleResponse
import models.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    fun loginUser(@Body user: UserLoginRequest): Call<GetScheduleResponse>

//    @POST("куда")
//    fun loginUser(@Body user: UserLoginRequest): Call<UserLoginResponse>

    @POST("Control")
    fun passengerControl(@Body data: ControlRequest): Call<Void>

//    @GET("куда")
//    fun getDailySchedules(): Call<GetScheduleResponse>

}
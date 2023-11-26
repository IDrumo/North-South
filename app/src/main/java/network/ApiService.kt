package network

import models.ApiResponse
import models.ScheduleResponse
import models.UserLoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
//    @GET("users/{token}")
//    suspend fun getUser(@Path("token") token: String): Call<>

    @POST("login")
    fun loginUser(@Body user: UserLoginRequest): Call<ApiResponse>

    @GET("schedule")
    fun getAllSchedule(): Call<ScheduleResponse>

}
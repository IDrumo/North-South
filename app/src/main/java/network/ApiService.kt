package network

import com.google.gson.Gson
import models.ScheduleResponse
import models.UserLoginRequest
import models.UserLoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
//    @GET("users/{token}")
//    suspend fun getUser(@Path("token") token: String): Call<>

    @POST("login")
    fun loginUser(@Body user: UserLoginRequest): Call<UserLoginResponse>

    @GET("schedule")
    fun getAllSchedule(): Call<ScheduleResponse>

}
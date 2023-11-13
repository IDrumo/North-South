package network

import models.UserLoginRequest
import models.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: String): UserLoginRequest

    @POST("/login")
    fun loginUser(@Body userLoginRequest: UserLoginRequest): Call<UserLoginResponse>

}
package network

import models.User
import models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: String): User

    @POST("login")
    suspend fun loginUser(@Body user: User): Response<UserResponse>

}
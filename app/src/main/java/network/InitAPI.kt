package network

import android.content.Context
import android.util.Log
import com.project.north_south.R
import com.project.north_south.subAlgorithms.ErrorMessage
import com.project.north_south.subAlgorithms.Storage
import com.project.north_south.subAlgorithms.calculateSHA256
import com.project.north_south.subAlgorithms.getFinishTime
import com.project.north_south.subAlgorithms.getToday
import models.ControlRequest
import models.UserLoginRequest
import models.UserLoginResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.text.SimpleDateFormat;
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TokenInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}

class RefreshTokenInterceptor(private val api: ApiService) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        if (response.code == 401) { // или другой код, указывающий на проблему с токеном
            // обновляем токен
            val newToken = api.refreshToken().execute().body()?.token

            // повторяем запрос с новым токеном
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
            return chain.proceed(newRequest)
        }

        return response
    }
}
class InitAPI() {
//    private val url: String = "https://spacekot.ru/apishechka/m/"
    private val url: String = "http://194.35.119.103/apishechka/m/"
    private val interceptor = HttpLoggingInterceptor()
    private var client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
//        .addInterceptor(TokenInterceptor("token"))
//        .addInterceptor(RefreshTokenInterceptor())
        .build()
    private var retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    private var api: ApiService = retrofit.create(ApiService::class.java)
    fun getAPI(): ApiService {
        return this.api
    }

    fun setToken(token: String): ApiService {
        this.client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .build()
        this.retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        this.api = retrofit.create(ApiService::class.java)
        return this.api
    }

    interface LoginCallback {
        fun onSuccess(response: UserLoginResponse)
        fun onError()
        fun onFailure(error: Throwable)
    }

    fun loginUser(login: String, password: String, callback: LoginCallback) {
        val protectLogin = calculateSHA256(login)
        val protectPassword = calculateSHA256(password)

        api.loginUser(UserLoginRequest(protectLogin, protectPassword, getToday()))
            .enqueue(object : Callback<UserLoginResponse> {
                override fun onResponse(
                    call: Call<UserLoginResponse>,
                    response: Response<UserLoginResponse>
                ) {
                    if (response.isSuccessful) {
                        callback.onSuccess(response.body()!!)
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                    callback.onFailure(t)
//                    Log.d("MyLog", "login: ${t.message}")
                }
            })
    }

    interface SaveDataCallback{

        fun onSuccess()
        fun onError(e: String)
        fun onFailure(e: Throwable)
    }

    fun sendData (context: Context, callback: SaveDataCallback) {
        val error = ErrorMessage(context)
        val storage = Storage(context)

        val trip = storage.getTrip()
        val (tickets, time) = storage.getControl()
        val request = ControlRequest(trip.id, getFinishTime(), time, tickets)


        api.passengerControl(request)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        callback.onSuccess()
                    } else {
                        callback.onError(response.body().toString())
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    callback.onFailure(t)
                }
            })
    }
}
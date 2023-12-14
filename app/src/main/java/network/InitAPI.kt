package network

import android.content.Context
import android.util.Log
import com.project.north_south.R
import com.project.north_south.subAlgorithms.ErrorMessage
import com.project.north_south.subAlgorithms.Storage
import com.project.north_south.subAlgorithms.calculateSHA256
import models.ControlRequest
import models.UserLoginRequest
import models.UserLoginResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.text.SimpleDateFormat;

class InitAPI() {
//    private val url: String = "https://spacekot.ru/apishechka/m/"
    private val url: String = "http://194.35.119.103/apishechka/m/"
    private val interceptor = HttpLoggingInterceptor()
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    private val api: ApiService = retrofit.create(ApiService::class.java)
    fun getAPI(): ApiService {
        return this.api
    }

    interface LoginCallback {
        fun onSuccess(response: UserLoginResponse)
        fun onError()
        fun onFailure(error: Throwable)
    }

    interface SaveDataCallback{

        fun onSuccess()
        fun onError(e: String)
        fun onFailure(e: Throwable)
    }

    fun loginUser(login: String, password: String, callback: LoginCallback) {
        val protectLogin = calculateSHA256(login)
        val protectPassword = calculateSHA256(password)

        api.loginUser(UserLoginRequest(protectLogin, protectPassword))
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

    fun sendData (context: Context, callback: SaveDataCallback) {
        val error = ErrorMessage(context)
        val storage = Storage(context)

        val trip = storage.getTrip()

        val (tickets, time) = storage.getControl()

        val calendar: Calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("HH:mm:ss")
        val finish_time: String = dateFormat.format(calendar.getTime())

        val request = ControlRequest(trip.id, finish_time, time, tickets)


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
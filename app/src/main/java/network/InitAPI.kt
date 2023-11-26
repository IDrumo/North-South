package network

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.project.north_south.R
import models.ApiResponse
import models.UserLoginRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InitAPI(url: String) {
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
    fun loginUser(context: Context, login: String, password: String) {
        val api = InitAPI(context.getString(R.string.base_url)).getAPI()
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

        api.loginUser(UserLoginRequest(login, password))
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful) {
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("active", true)
                        editor.putString("login", login)
                        editor.putString("password", password)
                        editor.putString("token", response.body()?.data?.token)
                        editor.putString("role", response.body()?.data?.role)
                        editor.putString("first_name", response.body()?.data?.first_name)
                        editor.putString("last_name", response.body()?.data?.last_name)
                        editor.putString("patronymic", response.body()?.data?.patronymic)
                        editor.apply()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.not_found_error_message),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.conection_error_message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
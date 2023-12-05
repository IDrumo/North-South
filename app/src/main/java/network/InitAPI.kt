package network

import android.content.Context
import com.project.north_south.R
import com.project.north_south.subAlgorithms.ErrorMessage
import com.project.north_south.subAlgorithms.Storage
import models.UserLoginRequest
import models.UserLoginResponse
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
    fun loginUser (login: String, password: String, context: Context) {
        val api = InitAPI(context.getString(R.string.base_url)).getAPI()

        val error = ErrorMessage(context)

        api.loginUser(UserLoginRequest(login, password))
            .enqueue(object : Callback<UserLoginResponse> {
                override fun onResponse(
                    call: Call<UserLoginResponse>,
                    response: Response<UserLoginResponse>
                ) {
                    if (response.isSuccessful) {
                        Storage(context).saveUserInfo(login, password, response.body())
                    } else {
                        error.not_found_error()
                    }

                }

                override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                    error.connection_error()
                }
            })
    }


}
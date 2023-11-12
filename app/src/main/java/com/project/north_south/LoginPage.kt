package com.project.north_south

import android.app.ActivityOptions
import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.project.north_south.databinding.ActivityLoginPageBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson
import com.google.gson.JsonObject
import models.User
import network.ApiService
import okhttp3.FormBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit

class LoginPage : AppCompatActivity() {
    lateinit var binding: ActivityLoginPageBinding

    lateinit var requestQueue: RequestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }


//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
//        var user = User("abobus", "abobus")
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://spacekot.ru/apishechka/login/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//        val apiService = retrofit.create(ApiService::class.java)


        val intent = Intent(this, AccountMenu::class.java)

        binding.loginButton.setOnClickListener {
            val login = binding.loginField.editText?.text.toString().trim()
            val password = binding.passwordField.editText?.text.toString()
//            user = User(login, password)
            if (login == "" || password == "") {
                if (login == "") binding.loginField.setError(getString(R.string.login_errer_message))
                if (password == "") binding.passwordField.setError(getString(R.string.password_errer_message))
                Toast.makeText(this, R.string.toast_error_message, Toast.LENGTH_SHORT).show()
            } else {
                //---------------------------------------

                doLogin(login, password, intent)



//
//                val request = Request.Builder()
//                    .url("https://spacekot.ru/apishechka/login")
//                    .post(requestBody)
//                    .build()

//                client.newCall(request).enqueue(object : Callback {
//
//                    override fun onFailure(call: Call, e: IOException) {
//                        runOnUiThread {
//                            binding.tvEnter.text = getString(R.string.conection_error_message)
//                            Log.d("MyLog", e.message.toString())
//                        }
//                    }
//
//                    override fun onResponse(call: Call, response: Response) {
//                        //получили ответ <------
//                        runOnUiThread {
//                            // запускаем обработку в новом потоке
//                            val responseBody = response.body()?.string()
//                            if (response.isSuccessful && responseBody != null) {
//                                val gson = Gson()
//                                val jsonResponse = gson.fromJson(responseBody, JsonObject::class.java)
//
//                                    intent.putExtra("login", login)
//                                        .putExtra("password", password)
//                                        .putExtra("token", jsonResponse.get("token").toString())
//                                        .putExtra("role", jsonResponse.get("role").toString())
//                                    Toast.makeText(applicationContext, "Вы вошли", Toast.LENGTH_LONG).show()
//                                    startActivity(intent)
//                                    finish()
//                                }
//                        }
//                    }
//                })
                //---------------------------------------------------------------------------------
            }
        }

    }

    fun doLogin(login: String, password: String, intent: Intent) {
        val url = "https://spacekot.ru/apishechka/login/"
        val body = "\"login\":\"${login}\"," +
                "\"password\":\"${password}\""
        val request = JSONObject()
            .put("login", login).
            put("password", password)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, request,
            { response ->
                if (response.get("Response") == "False") {
                    Log.d("MyLog", getString(R.string.not_found_error_message))
                } else {
                    intent.putExtra("login", login)
                        .putExtra("password", password)
                        .putExtra("token", response.get("token").toString())
                        .putExtra("role", response.get("role").toString())
                    Log.d("MyLog", "LKJAGFLKJASH")
                    startActivity(intent)
                    finish()

                }
            },
            { error ->
                Log.d("MyLog", error.toString())
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

//    private fun doLogin(login : String, password : String, intent: Intent, apiService: ApiService, user: User){
//        GlobalScope.launch {
//            try {
//                val response = apiService.loginUser(user)
//                if (response.isSuccessful) {
//                    val createdData = response.body()!!
//
//                    intent.putExtra("login", login)
//                        .putExtra("password", password)
//                        .putExtra("role", createdData.role)
//                        .putExtra("token", createdData.token)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    Log.d("MyLog", getString(R.string.not_found_error_message))
//                }
//            } catch (e: Exception) {
//                Log.d("MyLog", getString(R.string.conection_error_message))
//            }
//        }
//    }


//    class NetworkTask : CoroutineScope by MainScope() {
//        fun execute() {
//            launch {
//                // Выполнение сетевых операций здесь
//                val response = withContext(Dispatchers.IO) {
//                    val url = URL("https://spacekot.ru/apishechka/login")
//                    val connection = url.openConnection() as HttpURLConnection
//                    // Другие настройки и чтение ответа от сервера
//                    response
//                }
//                // Обработка результата после выполнения сетевых операций
//            }
//        }
//
//        fun cancel() {
//            cancel()
//        }
//    }

}


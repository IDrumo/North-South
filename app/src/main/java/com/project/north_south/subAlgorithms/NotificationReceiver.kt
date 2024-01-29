package com.project.north_south.subAlgorithms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.project.north_south.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class NotificationReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Создание канала уведомлений (требуется для Android 8.0 и выше)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "my_channel_id"
            val channelName = "My Channel"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Получение времени ближайшего рейса из intent или другого источника данных
        val nextFlightTime = intent.getStringExtra("next_flight_time") // Время ближайшего рейса
//        Log.d("MyLog", "Мяу из $nextFlightTime")

        val flightTime = LocalTime.parse(nextFlightTime, DateTimeFormatter.ofPattern("HH:mm"))
        if (LocalTime.now().isAfter(flightTime)) {
            return // Если время рейса уже прошло, прекращаем выполнение метода
        }

        // Создание уведомления
        val notificationBuilder = NotificationCompat.Builder(context, "my_channel_id")
            .setContentTitle("Ближайший рейс")
            .setContentText("Время: $nextFlightTime")
            .setSmallIcon(R.drawable.ic_new_launcher)
            .setAutoCancel(true)

        // Отображение уведомления
        notificationManager.notify(0, notificationBuilder.build())
    }
}
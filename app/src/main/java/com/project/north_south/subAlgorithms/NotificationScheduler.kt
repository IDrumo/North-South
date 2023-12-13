import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.project.north_south.subAlgorithms.NotificationReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NotificationScheduler(private val context: Context) {

    fun scheduleNotifications(eventTimes: List<String>) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for (eventTime in eventTimes) {
            val notificationTime = calculateNotificationTime(eventTime)
            val intent = Intent(context, NotificationReceiver::class.java)
                .putExtra("next_flight_time", eventTime)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_IMMUTABLE)

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, notificationTime.timeInMillis, pendingIntent)
        }
    }

    private fun calculateNotificationTime(eventTime: String): Calendar {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val eventDateTime = sdf.parse(eventTime)
        val notificationTime = Calendar.getInstance()
        if (eventDateTime != null) {
            val eventCalendar = Calendar.getInstance()
            eventCalendar.time = eventDateTime

            notificationTime.apply {
                set(Calendar.HOUR_OF_DAY, eventCalendar.get(Calendar.HOUR_OF_DAY))
                set(Calendar.MINUTE, eventCalendar.get(Calendar.MINUTE))
                set(Calendar.SECOND, eventCalendar.get(Calendar.SECOND))
            }
        }


        Log.d("MyLog", "До минуса ${notificationTime.time}")
        notificationTime.add(Calendar.MINUTE, -30)
        Log.d("MyLog", "После минуса ${notificationTime.time}")

        return notificationTime
    }


    fun cancelNotifications() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        alarmManager.cancel(pendingIntent)
    }
}

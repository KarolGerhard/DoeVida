package br.com.akgs.doevida.infra.remote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import br.com.akgs.doevida.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
//        saveTokenServer(token)
        // Handle the new token here, e.g., send it to your server
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: "Nova solicitação de doação"
        val body = remoteMessage.notification?.body ?: "Você tem uma nova solicitação de doação compativel com seu tipo sanguíneo."

        showNotification(
            title = title,
            body = body
        )
    }

    private fun showNotification(title: String, body: String) {
        val channelId = "donation_channel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Solicitações de doação",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para notificações de solicitações de doação"
                enableLights(true)
                enableVibration(true)
            }

            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(br.com.akgs.doevida.R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

//        notificationManager.notify(System.currentTimeMillis().toInt(), notification.build)
    }

//    private fun saveTokenServer(token: String) {
//        val firebaseAuth = FirebaseAuth.getInstance()
//        val userId = firebaseAuth.currentUser?.uid ?: return
//
//        val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)
//        userRef.update("fcmToken", token)
//
//    }
}
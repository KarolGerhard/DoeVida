package br.com.akgs.doevida.infra

import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.messaging

class NotificationManager(
    private val firebaseDatabaseService: FirebaseDatabaseService,
) {
    private val messaging = FirebaseMessaging.getInstance()

    suspend fun subscribeToBloodType(bloodType: String) {
        try {
            messaging.subscribeToTopic(bloodType)
            println("Subscribed to topic: $bloodType")
        } catch (e: Exception) {
            println("Failed to subscribe to topic: $bloodType")
        }
    }

    suspend fun unsubscribeFromBloodType(bloodType: String) {
        try {
            messaging.unsubscribeFromTopic(bloodType)
            println("Unsubscribed from topic: $bloodType")
        } catch (e: Exception) {
            println("Failed to unsubscribe from topic: $bloodType")
        }
    }

     fun sendNotification(bloodType: String, message: String, title: String) {
        try {
            firebaseDatabaseService.sendNotification(
                topic = bloodType,
                title = title,
                body = message
            ) { success, error ->
                if (success) {
                    println("Notification sent successfully to topic: $bloodType")
                } else {
                    println("Failed to send notification to topic: $bloodType. Error: $error")
                }
            }
        } catch (e: Exception) {
            println("Failed to send notification: ${e.message}")
        }
    }

}
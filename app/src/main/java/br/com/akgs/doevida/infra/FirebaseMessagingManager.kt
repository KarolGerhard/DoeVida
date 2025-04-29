package br.com.akgs.doevida.infra

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import javax.inject.Inject

class FirebaseMessagingManager @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging
) {
    fun subscribeToTopic(topic: String) {
        firebaseMessaging.subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Você se inscreveu no tópico: $topic")
                } else {
                    println("Erro ao se inscrever no tópicoc: $topic")
                }
            }
    }

    fun unsubscribeFromTopic(topic: String) {
        firebaseMessaging.unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Unsubscribed from topic: $topic")
                } else {
                    println("Failed to unsubscribe from topic: $topic")
                }
            }
    }
}
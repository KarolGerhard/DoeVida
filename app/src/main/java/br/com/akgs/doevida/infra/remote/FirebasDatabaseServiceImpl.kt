package br.com.akgs.doevida.infra.remote

import android.content.ContentValues.TAG
import android.util.Log
import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.infra.remote.entities.User
import com.google.firebase.firestore.FirebaseFirestore

class FirebasDatabaseServiceImpl : FirebaseDatabaseService {
    private val firestore = FirebaseFirestore.getInstance()

    override fun getUserById(id: String, onComplete: (User?, String?) -> Unit) {
       firestore.collection("users").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    onComplete(
                        User(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        bloodType = document.getString("bloodType") ?: "",
                        phone = document.getString("phone") ?: "",
                        email = document.getString("email") ?: "",
                        city = document.getString("city") ?: "",
                        state = document.getString("state") ?: ""
                    ), null)
                } else {
                    onComplete(null, "Usuário não encontrado")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting user", exception)
                onComplete(null, exception.message)
            }
    }

    override fun addUser(user: User, onComplete: (Boolean, String?) -> Unit) {
        firestore.collection("users").document(user.id).set(user)
            .addOnSuccessListener {
                onComplete(true, null)
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }
    }

    override fun createRequestDonation(requestDonation: RequestDonation, onComplete: (Boolean, String?) -> Unit) {
        firestore.collection("REQUEST_DONATION").add(requestDonation)
            .addOnSuccessListener { documentReference ->
                onComplete(true, documentReference.id)
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }
    }

    override fun updateRequestDonation(requestDonationId: String, requestDonationStatus: String, onComplete: (Boolean, String?) -> Unit) {
        firestore.collection("REQUEST_DONATION").document(requestDonationId)
            .update("status", requestDonationStatus)
            .addOnSuccessListener {
                onComplete(true, null)
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }
    }

    override fun deletRequestDonation(requestDonation: RequestDonation) {
        firestore.collection("REQUEST_DONATION").document(requestDonation.id).delete()
            .addOnSuccessListener {
                Log.d(TAG, "Request donation deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting request donation", e)
            }
    }

    override fun updateUser(user: User) {
        firestore.collection("users").document(user.id).set(user)
            .addOnSuccessListener {
                Log.d(TAG, "User updated successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating user", e)
            }
    }

    override fun createDonation(donation: Donation, onComplete: (Boolean, String?)-> Unit){
        firestore.collection("DONATION").add(donation)
            .addOnSuccessListener { documentReference ->
                onComplete(true, documentReference.id)
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }
    }

    override fun updateDonation(donationId: String, donationLocal: String, donationDate: String, onComplete: (Boolean, String?) -> Unit) {
        firestore.collection("DONATION").document(donationId)
            .update("localDonation", donationLocal, "dateDonation", donationDate)
            .addOnSuccessListener {
                onComplete(true, null)
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }
    }

    override fun getDonationsByUser(userId: String, onComplete: (List<Donation>?, String?) -> Unit) {
        firestore.collection("DONATION")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val donations = result.map { document ->
                    Donation(
                        id = document.id,
                        userId = document.getString("userId") ?: "",
                        localDonation = document.getString("localDonation") ?: "",
                        dateDonation = document.getString("dateDonation") ?: ""
                    )
                }
                onComplete(donations, null)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting donations by user", exception)
                onComplete(null, exception.message)
            }
    }


    override fun getAcceptedDonationsByUser(userId: String, onComplete: (RequestDonation?, String?) -> Unit) {
        firestore.collection("REQUEST_DONATION")
            .whereEqualTo("userId", userId)
            .whereEqualTo("status", "Aceito")
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    onComplete(null, null)
                } else {
                    val requestDonation = result.documents.map {
                        RequestDonation(
                            id = it.id,
                            userId = it.getString("userId") ?: "",
                            name = it.getString("name"),
                            phone = it.getString("phone"),
                            bloodType = it.getString("bloodType") ?: "",
                            city = it.getString("city") ?: "",
                            state = it.getString("state") ?: "",
                            status = it.getString("status") ?: "",
                            local = it.getString("local") ?: ""
                        )
                    }
                    onComplete(requestDonation.last(), null)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting accepted donations by user", exception)
                onComplete(null, exception.message)
            }
    }

    override fun getSolicitationsByCity(state: String, city: String, onComplete: (List<RequestDonation>?, String?) -> Unit) {
        firestore.collection("REQUEST_DONATION")
            .whereEqualTo("state", state)
            .whereEqualTo("city", city)
//            .orderBy("status", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                val requestDonations = result.map { document ->
                    RequestDonation(
                        id = document.id,
                        userId = document.getString("userId") ?: "",
                        name = document.getString("name"),
                        phone = document.getString("phone"),
                        bloodType = document.getString("bloodType") ?: "",
                        city = document.getString("city") ?: "",
                        state = document.getString("state") ?: "",
                        status = document.getString("status") ?: "",
                        local = document.getString("local") ?: ""
                    )
                }
                onComplete(requestDonations, null)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting solicitations by blood type and city", exception)
                onComplete(null, exception.message)
            }
    }

    override fun sendNotification(topic: String, title: String, body: String, onComplete: (Boolean, String?) -> Unit) {
            val notificationData = mapOf(
                "to" to "/topics/$topic",
                "notification" to mapOf(
                    "title" to title,
                    "body" to body
                )
            )

            // Simulação de envio de notificação (substitua por uma chamada HTTP real se necessário)
            firestore.collection("NOTIFICATIONS").add(notificationData)
                .addOnSuccessListener {
                    onComplete(true, null)
                }
                .addOnFailureListener { e ->
                    onComplete(false, e.message)
                }
        }



}
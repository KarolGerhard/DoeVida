package br.com.akgs.doevida.infra.remote

import android.content.ContentValues.TAG
import android.util.Log
import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.infra.remote.entities.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject

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

    override fun getDonation(donation: String) {
        firestore.collection("SOLICITACOES").document(donation).get()
    }

    override fun updateDonation(requestDonationId: String, requestDonationStatus: String, onComplete: (Boolean, String?) -> Unit) {
        firestore.collection("SOLICITACOES").document(requestDonationId)
            .update("status", requestDonationStatus)
            .addOnSuccessListener {
                onComplete(true, null)
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }
    }

    override fun deleteDonation(requestDonation: RequestDonation) {
        firestore.collection("SOLICITACOES").document(requestDonation.id).delete()
    }

    override fun getUserSolicitations(userId: String): ArrayList<RequestDonation> {
        val requestDonations = ArrayList<RequestDonation>()
        firestore.collection("SOLICITACOES")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val requestDonation = document.toObject(RequestDonation::class.java)
                    requestDonations.add(requestDonation)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting user solicitations", exception)
            }
        return requestDonations
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

}
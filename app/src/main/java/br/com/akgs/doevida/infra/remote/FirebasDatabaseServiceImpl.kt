package br.com.akgs.doevida.infra.remote

import android.content.ContentValues.TAG
import android.util.Log
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.infra.remote.entities.User
import com.google.firebase.firestore.FirebaseFirestore

class FirebasDatabaseServiceImpl : FirebaseDatabaseService {
    private val firestore = FirebaseFirestore.getInstance()

    override fun getUserById(id: String): User? {
        var user: User? = null
        firestore.collection("users").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    user = document.toObject(User::class.java)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        return user
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

    override fun updateRequestDonation(requestDonation: RequestDonation) {
        firestore.collection("REQUEST_DONATION").document(requestDonation.id).set(requestDonation)
            .addOnSuccessListener {
                Log.d(TAG, "Request donation updated successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating request donation", e)
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

    override fun createDonation(requestDonation: RequestDonation): String {
        var mensage: String = ""
        firestore.collection("SOLICITACOES").add(requestDonation)
            .addOnSuccessListener { documentReference ->
                documentReference.id
                mensage = "Success"
            }.addOnFailureListener { solicitation ->
            mensage = solicitation.message.toString()
            Log.w("TAG", "Error adding document", solicitation)
        }
        return mensage
    }

    override fun getDonation(donation: String) {
        firestore.collection("SOLICITACOES").document(donation).get()
    }

    override fun updateDonation(requestDonation: RequestDonation) {
        firestore.collection("SOLICITACOES").document(requestDonation.id).set(requestDonation)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
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
            .get()
            .addOnSuccessListener { result ->
                val requestDonations = result.map { document ->
                    document.toObject(RequestDonation::class.java)
                }
                onComplete(requestDonations, null)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting solicitations by blood type and city", exception)
                onComplete(null, exception.message)
            }
    }

}
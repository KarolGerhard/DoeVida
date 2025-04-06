package br.com.akgs.doevida.infra.remote

import android.content.ContentValues.TAG
import android.util.Log
import br.com.akgs.doevida.infra.remote.entities.Donation
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

    override fun addUser(user: User) {
        firestore.collection("users").document(user.id).set(user)
            .addOnSuccessListener {
                Log.d(TAG, "User added successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding user", e)
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

    override fun createDonation(donation: Donation): String {
        var mensage: String = ""
        firestore.collection("SOLICITACOES").add(donation).addOnSuccessListener { documentReference ->
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

    override fun updateDonation(donation: Donation) {
        firestore.collection("SOLICITACOES").document(donation.id).set(donation)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
    }

    override fun deleteDonation(donation: Donation) {
        firestore.collection("SOLICITACOES").document(donation.id).delete()
    }

    override fun getUserSolicitations(userId: String): ArrayList<Donation> {
        val donations = ArrayList<Donation>()
        firestore.collection("SOLICITACOES")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val donation = document.toObject(Donation::class.java)
                    donations.add(donation)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting user solicitations", exception)
            }
        return donations
    }
}
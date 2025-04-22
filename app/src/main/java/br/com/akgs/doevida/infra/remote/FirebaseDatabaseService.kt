package br.com.akgs.doevida.infra.remote

import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.User

interface FirebaseDatabaseService {
    fun getUserById(id: String): User?
    fun updateUser(user: User)
    fun createDonation(donation: Donation): String
    fun updateDonation(donation: Donation)
    fun deleteDonation(donation: Donation)
    fun getDonation(donation: String)
    fun getUserSolicitations(userId: String): ArrayList<Donation>
    fun addUser(user: User, onComplete: (Boolean, String?) -> Unit)
}

package br.com.akgs.doevida.infra.remote

import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.infra.remote.entities.User

interface FirebaseDatabaseService {
    fun getUserById(id: String): User?
    fun updateUser(user: User)
    fun createDonation(requestDonation: RequestDonation): String
    fun updateDonation(requestDonation: RequestDonation)
    fun deleteDonation(requestDonation: RequestDonation)
    fun getDonation(donation: String)
    fun getUserSolicitations(userId: String): ArrayList<RequestDonation>
    fun addUser(user: User, onComplete: (Boolean, String?) -> Unit)

    fun createRequestDonation(requestDonation: RequestDonation, onComplete: (Boolean, String?) -> Unit)
    fun updateRequestDonation(requestDonation: RequestDonation)
    fun deletRequestDonation(requestDonation: RequestDonation)
    fun getSolicitationsByCity(state: String, city: String, onComplete: (List<RequestDonation>?, String?) -> Unit)

}

package br.com.akgs.doevida.infra.remote

import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.infra.remote.entities.User

interface FirebaseDatabaseService {
    fun getUserById(id: String, onComplete: (User?, String?) -> Unit)
    fun updateUser(user: User)
    fun createDonation(donation: Donation,  onComplete: (Boolean, String?)-> Unit)
    fun updateDonation(donationId: String, donationLocal: String, donationDate: String, onComplete: (Boolean, String?) -> Unit)

    fun getDonationsByUser(userId: String, onComplete: (List<Donation>?, String?) -> Unit)
    fun getAcceptedDonationsByUser(userId: String, onComplete: (RequestDonation?, String?) -> Unit)
    fun addUser(user: User, onComplete: (Boolean, String?) -> Unit)

    fun createRequestDonation(requestDonation: RequestDonation, onComplete: (Boolean, String?) -> Unit)
    fun updateRequestDonation(requestDonationId: String, requestDonationStatus: String, onComplete: (Boolean, String?) -> Unit)
    fun deletRequestDonation(requestDonation: RequestDonation)
    fun getSolicitationsByCity(state: String, city: String, onComplete: (List<RequestDonation>?, String?) -> Unit)
    fun sendNotification(topic: String, title: String, body: String, onComplete: (Boolean, String?) -> Unit)

}

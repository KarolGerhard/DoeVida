package br.com.akgs.doevida.infra.repository

import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.RequestDonation

class DonationRepositoryImpl(private val firebaseDatabaseService: FirebaseDatabaseService) : DonationRepository {

//   override fun createDonation(donation: Donation) {
//        return firebaseDatabaseService.createDonation(donation)
//    }

    override fun getDonation(donationId: String) {
        firebaseDatabaseService.getDonation(donationId)
    }

//    override fun updateDonation(requestDonation: RequestDonation) {
//        firebaseDatabaseService.updateDonation(requestDonation)
//    }

    override fun deleteDonation(requestDonation: RequestDonation) {
        firebaseDatabaseService.deleteDonation(requestDonation)
    }

    override  fun getUserSolicitations(userId: String): ArrayList<RequestDonation> {
        return firebaseDatabaseService.getUserSolicitations(userId)
    }
}
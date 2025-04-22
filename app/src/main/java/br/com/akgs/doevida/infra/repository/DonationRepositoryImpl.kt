package br.com.akgs.doevida.infra.repository

import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.infra.remote.entities.Donation

class DonationRepositoryImpl(private val firebaseDatabaseService: FirebaseDatabaseService) : DonationRepository {

   override fun createDonation(donation: Donation): String {
        return firebaseDatabaseService.createDonation(donation)
    }

    override fun getDonation(donationId: String) {
        firebaseDatabaseService.getDonation(donationId)
    }

    override fun updateDonation(donation: Donation) {
        firebaseDatabaseService.updateDonation(donation)
    }

    override fun deleteDonation(donation: Donation) {
        firebaseDatabaseService.deleteDonation(donation)
    }

    override  fun getUserSolicitations(userId: String): ArrayList<Donation> {
        return firebaseDatabaseService.getUserSolicitations(userId)
    }
}
package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.infra.repository.DonationRepository

class DonationUseCaseImpl(
    private val donationRepository: DonationRepository,
    private val firebaseDatabaseService: FirebaseDatabaseService
) : DonationUseCase {

    override fun getDonation(donationId: String) {
        return donationRepository.getDonation(donationId)
    }

    override fun createDonation(requestDonation: RequestDonation) {
        donationRepository.createDonation(requestDonation)
    }

    override fun updateDonation(requestDonation: RequestDonation) {
        donationRepository.updateDonation(requestDonation)
    }

    override fun deleteDonation(requestDonationId: RequestDonation) {
        donationRepository.deleteDonation(requestDonationId)
    }

    override fun getUserSolicitations(userId: String): ArrayList<RequestDonation> {
        return donationRepository.getUserSolicitations(userId)
    }

    override fun getSolicitationsByCity(state: String, city: String) {
        firebaseDatabaseService.getSolicitationsByCity(state, city) { donations, error ->
            if (error != null) {
                // Handle error
                println("Error fetching solicitations: $error")
            } else {
                // Handle success
                println("Solicitations fetched successfully: $donations")
            }
        }
    }
}
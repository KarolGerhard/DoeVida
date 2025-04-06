package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.repository.DonationRepository

class DonationUseCaseImpl(
    private val donationRepository: DonationRepository
) : DonationUseCase {

    override fun getDonation(donationId: String) {
        return donationRepository.getDonation(donationId)
    }

    override fun createDonation(donation: Donation) {
        donationRepository.createDonation(donation)
    }

    override fun updateDonation(donation: Donation) {
        donationRepository.updateDonation(donation)
    }

    override fun deleteDonation(donationId: String) {
        donationRepository.deleteDonation(donationId)
    }
}
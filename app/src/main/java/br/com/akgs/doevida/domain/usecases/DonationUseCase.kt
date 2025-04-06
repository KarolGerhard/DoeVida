package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.remote.entities.Donation

interface DonationUseCase {
    fun getDonation(donationId: String)
    fun createDonation(donation: Donation)
    fun updateDonation(donation: Donation)
    fun deleteDonation(donationId: String)
}
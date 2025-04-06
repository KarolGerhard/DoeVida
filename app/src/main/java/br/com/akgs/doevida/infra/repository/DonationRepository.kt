package br.com.akgs.doevida.infra.repository

import br.com.akgs.doevida.infra.remote.entities.Donation

interface DonationRepository {
    fun createDonation(donation: Donation): String
    fun getDonation(donationId: String)
    fun updateDonation(donation: Donation)
    fun deleteDonation(donation: String)
    fun getUserSolicitations(userId: String): ArrayList<Donation>
}
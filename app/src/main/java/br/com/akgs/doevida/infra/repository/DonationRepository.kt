package br.com.akgs.doevida.infra.repository

import br.com.akgs.doevida.infra.remote.entities.RequestDonation

interface DonationRepository {
//    fun createDonation(requestDonation: RequestDonation): String
    fun getDonation(donationId: String)
//    fun updateDonation(requestDonation: RequestDonation)
    fun getUserSolicitations(userId: String): ArrayList<RequestDonation>
    fun deleteDonation(requestDonation: RequestDonation)
}
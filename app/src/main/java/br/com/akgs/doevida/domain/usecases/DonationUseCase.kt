package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.remote.entities.RequestDonation

interface DonationUseCase {
    fun getDonation(donationId: String)
//    fun createDonation(requestDonation: RequestDonation)
//    fun updateDonation(requestDonation: RequestDonation)
    fun deleteDonation(requestDonationId: RequestDonation)
    fun getSolicitationsByCity(state: String, city: String)
    fun getUserSolicitations(userId: String): ArrayList<RequestDonation>
    fun getCompatibleBloodTypes(bloodType: String): List<String>
}
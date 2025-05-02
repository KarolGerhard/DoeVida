package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.infra.repository.DonationRepository

class DonationUseCaseImpl(
    private val firebaseDatabaseService: FirebaseDatabaseService
) : DonationUseCase {

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

    override fun getCompatibleBloodTypes(bloodType: String): List<String> {
        return when (bloodType.uppercase()) {
            "A+" -> listOf("A+", "A-", "O+", "O-")
            "A-" -> listOf("A-", "O-")
            "B+" -> listOf("B+", "B-", "O+", "O-")
            "B-" -> listOf("B-", "O-")
            "AB+" -> listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
            "AB-" -> listOf("A-", "B-", "AB-", "O-")
            "O+" -> listOf("O+", "O-")
            "O-" -> listOf("O-")
            else -> emptyList()
        }
    }

}
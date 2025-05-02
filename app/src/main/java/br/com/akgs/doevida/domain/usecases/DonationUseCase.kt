package br.com.akgs.doevida.domain.usecases

interface DonationUseCase {
    fun getSolicitationsByCity(state: String, city: String)
    fun getCompatibleBloodTypes(bloodType: String): List<String>
}
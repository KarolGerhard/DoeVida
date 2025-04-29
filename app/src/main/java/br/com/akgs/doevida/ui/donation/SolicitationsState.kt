package br.com.akgs.doevida.ui.donation

import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.RequestDonation

data class SolicitationsState (
    var navigateToHome: Boolean = false,
    var solitacoes: List<RequestDonation> = emptyList(),
    var isLoading: Boolean = false,
    var error: String? = null,
    val showDetails: Boolean = false,
    val tiposSanguineosCompativeis: List<String> = emptyList(),
    val donationAccepted: RequestDonation? = null,
)
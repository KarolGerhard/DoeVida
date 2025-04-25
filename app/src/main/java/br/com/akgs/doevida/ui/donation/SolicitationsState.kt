package br.com.akgs.doevida.ui.donation

import br.com.akgs.doevida.infra.remote.entities.RequestDonation

data class SolicitationsState (
    var navigateToHome: Boolean = false,
    var solitacoes: List<RequestDonation> = emptyList(),
)
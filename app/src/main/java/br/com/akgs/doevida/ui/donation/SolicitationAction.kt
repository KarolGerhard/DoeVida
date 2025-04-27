package br.com.akgs.doevida.ui.donation

import br.com.akgs.doevida.infra.remote.entities.RequestDonation

interface SolicitationAction {
    data object OnLaunch: SolicitationAction
    data class OnAccepted(val donation: RequestDonation) : SolicitationAction
    data object OnDismiss : SolicitationAction
    data object OnShowDetails : SolicitationAction
    data class OnSelectItem(val type: String) : SolicitationAction
}
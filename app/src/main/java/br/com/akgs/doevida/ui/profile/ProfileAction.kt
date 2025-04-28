package br.com.akgs.doevida.ui.profile

import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.ui.home.HomeAction

interface ProfileAction {
    data class OnSave(val donation: Donation) : ProfileAction
    data object OnDismiss : ProfileAction
    data object OnShowAdd : ProfileAction
    data object OnLougout : ProfileAction
    data object NavigateToLogin : ProfileAction

}
package br.com.akgs.doevida.ui.profile

import br.com.akgs.doevida.infra.remote.entities.Donation

interface ProfileAction {
    data class OnSave(val donation: Donation) : ProfileAction
    data object OnDismiss : ProfileAction
    data object OnLougout : ProfileAction
    data object NavigateToLogin : ProfileAction
    data object NavigateToMyDonation : ProfileAction
    data object NavigateToInformacion : ProfileAction
    data object OnEditMode : ProfileAction
    data class OnUpdatePhone(val phone: String) : ProfileAction
    data class OnUpdateBloodType(val bloodType: String) : ProfileAction
    data class OnUpdateCity(val city: String) : ProfileAction
    data class OnUpdateState(val state: String) : ProfileAction
    data object OnSaveUserInfo : ProfileAction
    data object OnToggleEstadoDropdown : ProfileAction
    data object OnToggleCidadeDropdown : ProfileAction
    data object OnBackClick : ProfileAction
    data object OnShowTermos : ProfileAction
}
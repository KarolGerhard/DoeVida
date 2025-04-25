package br.com.akgs.doevida.ui.donation

import br.com.akgs.doevida.ui.login.LoginAction

interface RequestDonationAction {
    data class OnLocalChange(val local: String) : RequestDonationAction
    data class OnNameChange(val name: String) : RequestDonationAction
    data class OnPhoneChange(val phone: String) : RequestDonationAction
    data class OnEstadoChange(val estado: String) : RequestDonationAction
    data class OnCidadeChange(val cidade: String) : RequestDonationAction
    data class OnDataNascChange(val birthDate: String) : RequestDonationAction
    data class OnTipoSanguineoChange(val bloodType: String) : RequestDonationAction
    data object OnContinueClick : RequestDonationAction
    data object OnSaveClick : RequestDonationAction
    data object OnLaunch: RequestDonationAction
}
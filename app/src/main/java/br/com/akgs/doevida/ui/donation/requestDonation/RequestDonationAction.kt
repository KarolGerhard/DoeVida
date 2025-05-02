package br.com.akgs.doevida.ui.donation.requestDonation

interface RequestDonationAction {
    data class OnLocalChange(val local: String) : RequestDonationAction
    data class OnNameChange(val name: String) : RequestDonationAction
    data class OnPhoneChange(val phone: String) : RequestDonationAction
    data class OnEstadoChange(val estado: String) : RequestDonationAction
    data class OnCidadeChange(val cidade: String) : RequestDonationAction
    data class OnTipoSanguineoChange(val bloodType: String) : RequestDonationAction
    data class OnTipoPedidoChange(val type: String) : RequestDonationAction
    data object OnSaveClick : RequestDonationAction
}
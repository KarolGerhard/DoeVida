package br.com.akgs.doevida.ui.profile

import br.com.akgs.doevida.infra.remote.entities.Donation

interface MyDonationAction {
    data object OnSaveDonation : MyDonationAction
    data object OnDismiss : MyDonationAction
    data object OnShowAdd : MyDonationAction
    data object OnDismissDatePicker : MyDonationAction
    data object OnShowDatePicker : MyDonationAction
    data class OnLocalChange(val local: String) : MyDonationAction
    data class OnDateChange(val date: String) : MyDonationAction
    data object OnBackClick : MyDonationAction
    data class OnDateSelected(val date: String) : MyDonationAction
}
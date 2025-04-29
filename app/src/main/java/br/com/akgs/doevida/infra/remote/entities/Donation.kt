package br.com.akgs.doevida.infra.remote.entities

data class Donation(
    var id: String,
    var userId: String = "",
//    var donationAccepted: RequestDonation? = null,
    var dateDonation: String = "",
    var localDonation: String = "",
)

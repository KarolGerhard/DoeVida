package br.com.akgs.doevida.infra.remote.entities

data class Donation(
    var userId: String = "",
    var donationAccepted: RequestDonation? = null,
    var dateDoation: String = "",
)

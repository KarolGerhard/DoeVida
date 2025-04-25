package br.com.akgs.doevida.infra.remote.entities

class RequestDonation(
    var id: String,
    var userId: String,
    var name: String? = "",
    var phone: String? = "",
    var bloodType: String,
    var city: String,
    var state: String,
    var status: String,
    var local: String,
) {
    override fun toString(): String {
        return "RequestDonation(id='$id', userId='$userId', name=$name, phone=$phone, bloodType=$bloodType, city=$city, state=$state, status=$status, local=$local)"
    }
}
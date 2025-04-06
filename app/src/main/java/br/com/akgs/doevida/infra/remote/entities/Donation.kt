package br.com.akgs.doevida.infra.remote.entities

class Donation(
    var id: String,
    var userId: String,
    var name: String? = "",
    var phone: String? = "",
    var bloodType: String,
    var city: String,
    var state: String,
    var status: String,
    var hospital: String,
) {
    override fun toString(): String {
        return "Solicitation(id='$id', userId='$userId', name=$name, phone=$phone, bloodType=$bloodType, city=$city, state=$state, status=$status, hospital=$hospital)"
    }
}
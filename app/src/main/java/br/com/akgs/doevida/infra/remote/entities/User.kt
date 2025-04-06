package br.com.akgs.doevida.infra.remote.entities

class User(
    var id: String,
    var name: String? = "",
    var email: String? = "",
    var phone: String? = "",
    var birthDate: String? = "",
    var bloodType: String? = "",
    var city: String? = "",
    var state: String? = ""
) {
    override fun toString(): String {
        return "User(id='$id', name=$name, email=$email, phone=$phone, birthDate=$birthDate, bloodType=$bloodType, city=$city, state=$state)"
    }
}
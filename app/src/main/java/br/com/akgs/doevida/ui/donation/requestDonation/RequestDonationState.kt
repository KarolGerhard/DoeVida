package br.com.akgs.doevida.ui.donation.requestDonation

import br.com.akgs.doevida.infra.remote.entities.RequestDonation

data class RequestDonationState (
    var name: String = "",
    var phone: String = "",
    var estado: String = "",
    var cidade : String = "",
    var estados: List<String> = emptyList(),
    var cidades: List<String> = emptyList(),
    var local: String = "",
    var tipoSanguineo: String = "",
    var tipoPedido: String = "", //arrumar nome
    val onNameChange: (String) -> Unit = {},
    val onPhoneChange: (String) -> Unit = {},
    val onEstadoChange: (String) -> Unit = {},
    val onCidadeChange: (String) -> Unit = {},
    val onLocalChange: (String) -> Unit = {},
    val onTipoSanguineoChange: (String) -> Unit = {},
    var navigateToHome: Boolean = false,
    var solitacoes: List<RequestDonation> = emptyList(),
)
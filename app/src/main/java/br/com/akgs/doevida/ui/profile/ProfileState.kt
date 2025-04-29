package br.com.akgs.doevida.ui.profile

import br.com.akgs.doevida.infra.Estado
import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.User

data class ProfileState(
    var donations: List<Donation> = emptyList(),
    var navigateToInformation: Boolean = false,
    var isLoading: Boolean = false,
    var error: String? = null,
    val showAdd: Boolean = false,
    val user: User? = null,
    val navigateToLogin: Boolean = false,
    val isEditMode: Boolean = false,
    val editPhone: String = "",
    val editName: String = "",
    val editBloodType: String = "",
    val editCity: String = "",
    val editState: String = "",
    val states: List<String> = emptyList(),
    val cities: List<String> = emptyList(),
    val isEstadoDropdownExpanded: Boolean = false,
    val isCidadeDropdownExpanded: Boolean = false,
    val showDiscardChangesDialog : Boolean = false,
    val showTermos: Boolean = false,
) {

}
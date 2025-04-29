package br.com.akgs.doevida.ui.profile

import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MyDonationState(
    var donations: List<Donation> = emptyList(),
    var donation: RequestDonation? = null,
    var navigateToInformation: Boolean = false,
    var isLoading: Boolean = false,
    var error: String? = null,
    val date: String = "",
    val local: String = "",
    val onDateChange : (String) -> Unit = {},
    val onLocalChange: (String) -> Unit = {},
    val showAddDonation: Boolean = false,
    val showDatePicker: Boolean = false,
)
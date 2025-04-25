package br.com.akgs.doevida.ui.donation

import androidx.lifecycle.ViewModel
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SolicitationViewModel(
    private val firebaseDatabaseService: FirebaseDatabaseService,
    private val firebaseAuthService: FirebaseAuthService,
) : ViewModel()  {

    private val _donationState = MutableStateFlow(SolicitationsState())
    val donationState = _donationState.asStateFlow()

    val user = firebaseDatabaseService.getUserById(
        firebaseAuthService.currentUser()?.id.toString()
    )

    init {
//        getSolicitationsByCity(user?.state, user?.city
//
//        )
    }

    private fun getSolicitationsByCity(state: String?, city: String?) {
        if (state != null) {
            if (city != null) {
                firebaseDatabaseService.getSolicitationsByCity(state, city) { donations, error ->
                    if (error != null) {
                        // Handle error
                        _donationState.value = _donationState.value.copy(
                            solitacoes = donations!!
                        )
                        println("Error fetching solicitations: $error")
                    } else {
                        // Handle success
                        println("Solicitations fetched successfully: $donations")
                    }
                }
            }
        }
    }
}
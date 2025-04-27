package br.com.akgs.doevida.ui.donation

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.akgs.doevida.domain.usecases.DonationUseCase
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.infra.remote.entities.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SolicitationViewModel(
    private val firebaseDatabaseService: FirebaseDatabaseService,
    private val firebaseAuthService: FirebaseAuthService,
    private val donationUseCase: DonationUseCase
) : ViewModel() {

    private val _donationState = MutableStateFlow(SolicitationsState())
    val donationState = _donationState.asStateFlow()


    fun onAction(action: SolicitationAction) {
        when (action) {
            is SolicitationAction.OnLaunch -> onLaunch()
            is SolicitationAction.OnAccepted -> onAccepted(action.donation)
            is SolicitationAction.OnDismiss -> {
                _donationState.value = _donationState.value.copy(
                    navigateToHome = false,
                    showDetails = false
                )
            }
//            is SolicitationAction.OnShowDetails -> onShowDetails()
            is SolicitationAction.OnSelectItem -> onSelectItem(action.type)

        }
    }

    private fun onSelectItem(type: String) {
        _donationState.value = _donationState.value.copy(
            showDetails = true
        )
        getCompatibleBloodTypes(type)
    }



    private fun onAccepted(requestDonation: RequestDonation) {
       val donation = Donation(
           userId = _donationState.value.donationAccepted?.userId ?: "",
           donationAccepted = RequestDonation(
                id = requestDonation.id,
                userId = requestDonation.userId,
                name = requestDonation.name,
                phone = requestDonation.phone,
                local =  requestDonation.local,
                state = requestDonation.state,
                city = requestDonation.city,
                bloodType = requestDonation.bloodType,
                status = "Aceito",
           ),
           dateDoation = ""
        )
        firebaseDatabaseService.createDonation(donation) { success, error ->
            if (success) {
                Log.d("SolicitationViewModel", "Donation created successfully")
                _donationState.value = _donationState.value.copy(
                    donationAccepted = donation,
                    showDetails = false,

                )
            } else {
                Log.e("SolicitationViewModel", "Error creating donation: $error")
                _donationState.value = _donationState.value.copy(
                    error = error,
                    navigateToHome = false
                )
            }
        }
//        firebaseDatabaseService.updateRequestDonation(requestDonation.id, requestDonation.status) { success, error ->
//            if (success) {
//                Log.d("SolicitationViewModel", "Request donation updated successfully")
//            } else {
//                Log.e("SolicitationViewModel", "Error updating request donation: $error")
//            }
//        }

    }

    private fun onLaunch() {
        loadSolicitationsForCurrentUser()
    }

    private fun loadSolicitationsForCurrentUser() {
        _donationState.value = _donationState.value.copy(isLoading = true)

        val currentUser = firebaseAuthService.currentUser()
        if (currentUser.id.isEmpty()) {
            _donationState.value =
                _donationState.value.copy(isLoading = false, error = "Usuário não encontrado")
            return
        }
        firebaseDatabaseService.getUserById(currentUser.id) { user, error ->
            if (error != null) {
                _donationState.value = _donationState.value.copy(
                    isLoading = false,
                    error = error
                )
            } else if (user != null) {
                if (user.city.isEmpty() || user.state.isEmpty()) {
                    _donationState.value = _donationState.value.copy(
                        isLoading = false,
                        error = "Cidade ou estado não encontrados"
                    )
                } else {
                    getSolicitationsByCity(user.state, user.city)
                }
            } else {
                _donationState.value =
                    _donationState.value.copy(isLoading = false, error = "Usuário não encontrado")
            }
        }
    }

    private fun getSolicitationsByCity(estado: String, cidade: String) {
        firebaseDatabaseService.getSolicitationsByCity(estado, cidade) { solicitations, error ->
            _donationState.value = when {
                error != null -> {
                    _donationState.value.copy(
                        isLoading = false,
                        error = error,
                        solitacoes = emptyList()
                    )
                }

                solicitations.isNullOrEmpty() -> {
                    _donationState.value.copy(
                        isLoading = false,
                        error = "Nenhuma solicitação encontrada",
                        solitacoes = emptyList()
                    )
                }

                else -> {
                    _donationState.value.copy(
                        isLoading = false,
                        error = null,
                        solitacoes = solicitations
                    )
                }
            }
            onLaunch()
        }
    }

    fun getCompatibleBloodTypes(bloodType: String): List<String> {
        donationUseCase.getCompatibleBloodTypes(bloodType).let { bloodTypes ->
            _donationState.value = _donationState.value.copy(
                tiposSanguineosCompativeis = bloodTypes
            )
            return bloodTypes
        }

    }


}
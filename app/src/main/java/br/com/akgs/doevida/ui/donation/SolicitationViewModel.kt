package br.com.akgs.doevida.ui.donation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.akgs.doevida.domain.usecases.DonationUseCase
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import br.com.akgs.doevida.ui.profile.ProfileAction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SolicitationViewModel(
    private val firebaseDatabaseService: FirebaseDatabaseService,
    private val firebaseAuthService: FirebaseAuthService,
    private val donationUseCase: DonationUseCase
) : ViewModel() {

    private val _donationState = MutableStateFlow(SolicitationsState())
    val donationState = _donationState.asStateFlow()

    private val _actions = MutableSharedFlow<SolicitationAction>()
    val actions = _actions.asSharedFlow()

    val user = firebaseAuthService.currentUser()

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
            SolicitationAction.OnBackClick -> onBack()

        }
    }

    private fun emitAction(action: SolicitationAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun onBack() {
        emitAction(SolicitationAction.OnBackClick)
    }

    private fun onSelectItem(type: String) {
        _donationState.value = _donationState.value.copy(
            showDetails = true
        )
        getCompatibleBloodTypes(type)
    }


    private fun onAccepted(requestDonation: RequestDonation) {
        val requestDonation = RequestDonation(
            id = requestDonation.id,
            userId = user.id,
            name = requestDonation.name,
            phone = requestDonation.phone,
            local = requestDonation.local,
            state = requestDonation.state,
            city = requestDonation.city,
            bloodType = requestDonation.bloodType,
            status = "Aceito",
        )
        firebaseDatabaseService.updateRequestDonation(
            requestDonation.id,
            requestDonation.userId,
            requestDonation.status
        ) { success, error ->
            if (success) {
                Log.d("SolicitationViewModel", "Donation created successfully")
                _donationState.value = _donationState.value.copy(
                    donationAccepted = requestDonation,
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
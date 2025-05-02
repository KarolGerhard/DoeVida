package br.com.akgs.doevida.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.akgs.doevida.domain.usecases.DonationUseCase
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.infra.remote.entities.Donation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyDonationViewModel(
    private val authService: FirebaseAuthService,
    private val firebaseDatabaseService: FirebaseDatabaseService
) : ViewModel() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    private val _uiState = MutableStateFlow(MyDonationState())
    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<MyDonationAction>()
    val actions = _actions.asSharedFlow()

    val user = authService.currentUser()


    fun onAction(action: MyDonationAction) {
        when (action) {
            is MyDonationAction.OnSaveDonation -> onSave()
            is MyDonationAction.OnDismiss -> onDismiss()
            is MyDonationAction.OnShowAdd -> onShowAdd()
            is MyDonationAction.OnDismissDatePicker -> onDismissDismissDatePicker()
            is MyDonationAction.OnShowDatePicker -> onShowAddShowDatePicker()
            is MyDonationAction.OnLocalChange -> onLocalChange(action.local)
            is MyDonationAction.OnDateChange -> onDateChange(action.date)
            is MyDonationAction.OnDateSelected -> onDateSelected(action.date)
            MyDonationAction.OnBackClick -> onBack()
        }
    }

    init {
        getDonationsAccepted()
        getDonationsByUser()
    }

    private fun getDonationsAccepted() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            firebaseDatabaseService.getAcceptedDonationsByUser(user.id) { donations, error ->
                if (donations != null) {
                    _uiState.value = _uiState.value.copy(
                        donation = donations,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error ?: "Erro ao buscar doações"
                    )
                }
            }
        }
    }

    private fun getDonationsByUser() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            firebaseDatabaseService.getDonationsByUser(user.id) { donations, error ->
                if (donations != null) {
                    _uiState.value = _uiState.value.copy(
                        donations = donations,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error ?: "Erro ao buscar doações"
                    )
                }
            }
        }
    }

    private fun onDateSelected(date: String) {
        val selectedDate = Date(date)
        _uiState.value = _uiState.value.copy(
            date = dateFormat.format(selectedDate),
            showDatePicker = false
        )

    }

    private fun onShowAddShowDatePicker() {
        _uiState.value = _uiState.value.copy(
            showDatePicker = true
        )
    }

    private fun onDismissDismissDatePicker() {
        _uiState.value = _uiState.value.copy(
            showDatePicker = false
        )
    }

    private fun onLocalChange(local: String) {
        _uiState.value = _uiState.value.copy(
            local = local
        )
    }

    private fun onDateChange(data: String) {
        _uiState.value = _uiState.value.copy(
            date = data
        )
    }

    private fun emitAction(action: MyDonationAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun onBack() {
        emitAction(MyDonationAction.OnBackClick)
    }


    private fun onDismiss() {
        _uiState.value = _uiState.value.copy(
            showAddDonation = false,
            local = "",
            date = "",
        )
    }


    private fun onShowAdd() {
        _uiState.value = _uiState.value.copy(
            showAddDonation = true
        )
    }

    private fun onSave() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            try {
                val donation = Donation(
                    userId = user.id,
                    localDonation = _uiState.value.local,
                    dateDonation = _uiState.value.date,
                    id = ""
                )

                firebaseDatabaseService.createDonation(donation) { success, error ->
                    if (success) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            showAddDonation = false,
                            local = "",
                            date = "",
                        )
                        emitAction(MyDonationAction.OnDismiss)
                        emitAction(MyDonationAction.OnSaveDonation)
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = error ?: "Erro ao salvar doação"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}


package br.com.akgs.doevida.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.akgs.doevida.domain.usecases.AuthUseCase
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.ui.login.LoginAction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authService: FirebaseAuthService,
    private val firebaseDatabaseService: FirebaseDatabaseService
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<HomeAction>()
    val actions = _actions.asSharedFlow()

    val user = authService.currentUser()

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ShowError -> showError(action.message)
            HomeAction.NavigateToSolicitation -> navigateToSolicitation()
            HomeAction.NavigateToHemocentros -> navigateToHemocentros()
        }
    }

    init {
        fetchUserData()
        fetchUserDonationsCount()
    }

    private fun emitAction(action: HomeAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun fetchUserData() {
        val userId = authService.getUserId()
        firebaseDatabaseService.getUserById(userId) { user, error ->
            if (user != null) {
                _uiState.value = _uiState.value.copy(
                    user = user
                )
            }
        }
    }

    fun fetchUserDonationsCount() {
        val userId = authService.getUserId()
        firebaseDatabaseService.getDonationsByUser(userId) { donations, error ->
            if (donations != null) {
                _uiState.value = _uiState.value.copy(
                    donationsCount = donations.size
                )
            } else if (error != null) {
                println("Erro ao buscar doações: ${error}")
            }
        }
    }


    private fun navigateToHemocentros() {
        TODO("Not yet implemented")
    }

    private fun navigateToSolicitation() {
        _uiState.value = _uiState.value.copy(
            navigateToSolicitation = true
        )
        emitAction(HomeAction.NavigateToSolicitation)
    }

    private fun showError(message: String) {
        TODO("Not yet implemented")
    }


}
package br.com.akgs.doevida.ui.home

import androidx.lifecycle.ViewModel
import br.com.akgs.doevida.domain.usecases.AuthUseCase
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val authService: FirebaseAuthService,
    private val authUseCase: AuthUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ShowError -> showError(action.message)
            HomeAction.NavigateToSolicitation -> navigateToSolicitation()
            HomeAction.NavigateToHemocentros -> navigateToHemocentros()
        }
    }

    private fun navigateToHemocentros() {
        TODO("Not yet implemented")
    }

    private fun navigateToSolicitation() {
        _uiState.value = _uiState.value.copy(
            navigateToSolicitation = true
        )

    }

    private fun showError(message: String) {
        TODO("Not yet implemented")
    }


}
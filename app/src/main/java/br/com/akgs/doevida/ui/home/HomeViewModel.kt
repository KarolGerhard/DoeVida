package br.com.akgs.doevida.ui.home

import androidx.lifecycle.ViewModel
import br.com.akgs.doevida.domain.usecases.AuthUseCase
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val authService: FirebaseAuthService,
    private val firebaseDatabaseService: FirebaseDatabaseService,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()


    val user = authService.currentUser()

    init {
        fetchUserData()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ShowError -> showError(action.message)
            HomeAction.NavigateToSolicitation -> navigateToSolicitation()
            HomeAction.NavigateToHemocentros -> navigateToHemocentros()
        }
    }

    private fun fetchUserData() {
        val userId = authService.currentUser().id
        firebaseDatabaseService.getUserById(userId) { user, error ->
            if (user != null) {
                _uiState.value = _uiState.value.copy(
                    user = user
                )
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

    }

    private fun showError(message: String) {
        TODO("Not yet implemented")
    }


}
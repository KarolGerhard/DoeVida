package br.com.akgs.doevida.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.ui.login.LoginAction
import br.com.akgs.doevida.ui.login.LoginState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val authService: FirebaseAuthService
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashState())
    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<SplashAction>()
    val actions = _actions.asSharedFlow()

    init {
        checkAuthAndNavigate()
    }

    private fun emitAction(action: SplashAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun checkAuthAndNavigate() {
        viewModelScope.launch {
            try {
                delay(2000)
                val user = authService.getUserId()
                val isAuthenticated = user.isNotEmpty()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isAuthenticated = isAuthenticated,
                )
                if (isAuthenticated) {
                    emitAction(SplashAction.OnNavigateToHome)
                } else {
                    emitAction(SplashAction.OnNavigateToAuth)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isAuthenticated = false,
                    error = e.message
                )
            }
        }
    }


}
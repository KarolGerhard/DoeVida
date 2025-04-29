package br.com.akgs.doevida.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.akgs.doevida.domain.usecases.AuthUseCase
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.ui.register.RegisterState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authUseCase: AuthUseCase,
     private val authService: FirebaseAuthService
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<LoginAction>()
    val actions = _actions.asSharedFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnEmailChange -> onEmailChange(action.email)
            is LoginAction.OnPasswordChange -> onPasswordChange(action.password)
            LoginAction.OnLoginClick -> onLoginClick()
            LoginAction.OnNewRegisterClick -> onNewRegisterClick()
        }
    }

    private fun emitAction(action: LoginAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun onNewRegisterClick() {
        _uiState.value = _uiState.value.copy(
            navigateToRegister = true
        )
        emitAction(LoginAction.OnNewRegisterClick)
    }

    private fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password
        )

    }

    private fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email
        )
    }


//    fun login(email: String, password: String): String {
//        return try {
//            authUseCase.login(email, password)
//            "Success"
//        } catch (e: Exception) {
//            "Failure: ${e.message}"
//        }
//    }

    private fun onLoginClick() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (email.isNotEmpty() && password.isNotEmpty()) {
            authService.signInWithEmailAndPassword(email, password) { success, error ->
                if (success) {
                    _uiState.value = _uiState.value.copy(
                        isLoggedIn = true
                    )
                    emitAction(LoginAction.OnLoginClick)
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoggedIn = false,
                        errorMessage = error.toString()
                    )
                }
            }
        }
    }


//
//    fun loginGoogle(
//
//        onComplete: (Boolean) -> Unit = {}
//    ){
//        authService.firebaseAuthWithGoogle(
//            onComplete = { success ->
//                if (success) {
//                    _uiState.value = LoginState(isLoggedIn = true)
//                } else {
//                    _uiState.value = LoginState(isLoggedIn = false)
//                }
//            }
//        )
//    }


}
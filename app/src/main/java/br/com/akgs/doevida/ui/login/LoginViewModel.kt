package br.com.akgs.doevida.ui.login

import androidx.lifecycle.ViewModel
import br.com.akgs.doevida.domain.usecases.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(
//    private val authService: FirebaseAuthService
    private val authUseCase: AuthUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnEmailChange -> onEmailChange(action.email)
            is LoginAction.OnPasswordChange -> onPasswordChange(action.password)
            LoginAction.OnLoginClick -> onLoginClick()
            LoginAction.OnNewRegisterClick -> onNewRegisterClick()
        }
    }

    private fun onNewRegisterClick() {
        _uiState.value = _uiState.value.copy(
            navigateToRegister = true
        )
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


    fun login(email: String, password: String): String {
        return try {
            authUseCase.login(email, password)
            "Success"
        } catch (e: Exception) {
            "Failure: ${e.message}"
        }
    }

    private fun onLoginClick() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (email.isNotEmpty() && password.isNotEmpty()) {
            authUseCase.login(email, password)
            _uiState.value = _uiState.value.copy(
                isLoggedIn = true,
                isLoginGoogle = false,
            )
        } else {
            _uiState.value = _uiState.value.copy(
                isLoggedIn = false
            )
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
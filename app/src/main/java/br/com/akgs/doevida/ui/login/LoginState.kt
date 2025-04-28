package br.com.akgs.doevida.ui.login

data class LoginState(
    var email: String = "",
    var password: String = "",
    var passwordValid: String = "",
    val onUserChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val isShowPassword: Boolean = false,
    val onTogglePasswordVisibility: () -> Unit = {},
    val isLoggedIn: Boolean = false,
    var isLoading: Boolean = false,
    var errorMessage: String = "",
    var isSuccess: Boolean = false,
    var isLoginGoogle: Boolean = false,
    var navigateToRegister: Boolean = false,
    var navigateToHome: Boolean = false
)
package br.com.akgs.doevida.ui.register

data class RegisterState(
    var email: String = "",
    var password: String = "",
    var passwordValid: String = "",
    var name: String = "",
    var phone: String = "",
    var estado: String = "",
    var estados: List<String> = emptyList(),
    var cidade: String = "",
    var cidades: List<String> = emptyList(),
    var dataNasc: String = "",
    var tipoSanguineo: String = "",
    var message: String = "",
    val onTipoSanguineoChange: (String) -> Unit = {},
    val isShowPassword: Boolean = false,
    val isNext: Boolean = false,
    val isLoggedIn: Boolean = false,
    var isLoading: Boolean = false,
    var errorMessage: String = "",
    var isSuccess: Boolean = false,
    var isLoginGoogle: Boolean = false,
    var navigateToLogin: Boolean = false,
    var navigateToHome: Boolean = false
)
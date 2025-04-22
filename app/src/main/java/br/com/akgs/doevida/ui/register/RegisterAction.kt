package br.com.akgs.doevida.ui.register

import br.com.akgs.doevida.ui.login.LoginAction

interface RegisterAction {
    data class OnEmailChange(val email: String) : RegisterAction
    data class OnPasswordChange(val password: String) : RegisterAction
    data class OnConfirmPasswordChange(val confirmPassword: String) : RegisterAction
    data class OnNameChange(val name: String) : RegisterAction
    data class OnPhoneChange(val phone: String) : RegisterAction
    data class OnEstadoChange(val estado: String) : RegisterAction
    data class OnCidadeChange(val cidade: String) : RegisterAction
    data class OnDataNascChange(val birthDate: String) : RegisterAction
    data class OnTipoSanguineoChange(val bloodType: String) : RegisterAction
    data object OnContinueClick : RegisterAction
    data object OnRegisterClick : RegisterAction
    data object OnLaunch: RegisterAction
}
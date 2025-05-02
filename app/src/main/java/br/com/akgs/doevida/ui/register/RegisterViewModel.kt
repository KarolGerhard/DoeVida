package br.com.akgs.doevida.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.akgs.doevida.domain.usecases.ReadJsonUseCase
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.infra.remote.entities.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val readJsonUseCase: ReadJsonUseCase,
    private val firebaseAuthService: FirebaseAuthService,
    private val firebaseDatabaseService: FirebaseDatabaseService
) : ViewModel() {
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()


    private val _actions = MutableSharedFlow<RegisterAction>()
    val actions = _actions.asSharedFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnEmailChange -> onEmailChange(action.email)
            is RegisterAction.OnPasswordChange -> onPasswordChange(action.password)
            is RegisterAction.OnConfirmPasswordChange -> onConfirmPasswordChange(action.confirmPassword)
            is RegisterAction.OnNameChange -> onNameChange(action.name)
            is RegisterAction.OnPhoneChange -> onPhoneChange(action.phone)
            is RegisterAction.OnEstadoChange -> onEstadoChange(action.estado)
            is RegisterAction.OnCidadeChange -> onCidadeChange(action.cidade)
            is RegisterAction.OnDataNascChange -> onDataNascChange(action.birthDate)
            is RegisterAction.OnTipoSanguineoChange -> onTipoSanguineoChange(action.bloodType)
            RegisterAction.OnContinueClick -> onContinueClick()
            RegisterAction.OnRegisterClick -> onRegisterClick()
        }
    }

    init {
        loadEstados()
    }

    private fun emitAction(action: RegisterAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun registerUser(user: User) {

        firebaseAuthService.createUser(user) { success, error ->
            if (success) {
                firebaseAuthService.signUpWithEmailAndPassword(user.email, user.password) { success, error ->
                    if (success) {
                        val userId = firebaseAuthService.getUserId()
                        user.id = userId
                        firebaseDatabaseService.addUser(user) { dbSuccess, error ->
                            if (dbSuccess) {
                                // Usuário criado com sucesso

                                _registerState.value = _registerState.value.copy(
                                    message = "Usuário criado com sucesso!",
                                    navigateToLogin = true,
                                    navigateToHome = true,
                                )
                                emitAction(RegisterAction.OnRegisterClick)

                            } else {
                                _registerState.value = _registerState.value.copy(
                                    message = "Erro ao criar usuário: ${error}"
                                )
                            }
                        }
                        _registerState.value = _registerState.value.copy(
                            isLoggedIn = true
                        )

                    } else {
                        _registerState.value = _registerState.value.copy(
                            isLoggedIn = false,
                            message = "Erro ao criar usuário: ${error}"
                        )
                    }
                }

            } else {
                _registerState.value = _registerState.value.copy(
                    isLoggedIn = false,
                    message = "Erro ao criar usuário: ${error}"
                )
            }
        }
    }


    private fun onRegisterClick() {
        viewModelScope.launch {
            val email = _registerState.value.email
            val password = _registerState.value.password
            val name = _registerState.value.name
            val phone = _registerState.value.phone
            val estado = _registerState.value.estado
            val cidade = _registerState.value.cidade
            val dataNasc = _registerState.value.dataNasc
            val tipoSanguineo = _registerState.value.tipoSanguineo

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {

                val user = User(
                    id = "",
                    name = name,
                    email = email,
                    phone = phone,
                    birthDate = dataNasc,
                    bloodType = tipoSanguineo,
                    city = cidade,
                    state = estado,
                    password = password
                )
                registerUser(user)
                _registerState.value = _registerState.value.copy(
                    isLoggedIn = true,
                    )
            } else {
                _registerState.value = _registerState.value.copy(
                    isLoggedIn = false
                )
            }
        }
    }

    private fun onEstadoChange(estado: String) {
        _registerState.value = _registerState.value.copy(
            estado = estado
        )
        val cidadesFiltradas = readJsonUseCase.getCidadesByEstado(estado)
        _registerState.value = _registerState.value.copy(cidades = cidadesFiltradas)
    }

    fun loadEstados() {
        val estadosJson = readJsonUseCase.readJsonEstados().map { it.sigla }
        _registerState.value = _registerState.value.copy(
            estados = estadosJson
        )
    }


    private fun onContinueClick() {
        _registerState.value = _registerState.value.copy(
            isNext = true
        )
    }

    private fun onTipoSanguineoChange(tipoSanguineo: String) {
        _registerState.value = _registerState.value.copy(
            tipoSanguineo = tipoSanguineo
        )
    }

    private fun onDataNascChange(dataNasc: String) {
        _registerState.value = _registerState.value.copy(
            dataNasc = dataNasc
        )
    }

    private fun onCidadeChange(city: String) {
        _registerState.value = _registerState.value.copy(
            cidade = city
        )

    }

    private fun onPhoneChange(phone: String) {
        _registerState.value = _registerState.value.copy(
            phone = phone
        )
    }

    private fun onNameChange(name: String) {
        _registerState.value = _registerState.value.copy(
            name = name
        )
    }

    private fun onConfirmPasswordChange(confirmPassword: String) {
        _registerState.value = _registerState.value.copy(
            passwordValid = confirmPassword
        )

    }

    private fun onPasswordChange(password: String) {
        _registerState.value = _registerState.value.copy(
            password = password
        )
    }

    private fun onEmailChange(email: String) {
        _registerState.value = _registerState.value.copy(
            email = email
        )
    }


}
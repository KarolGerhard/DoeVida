package br.com.akgs.doevida.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.akgs.doevida.domain.usecases.ReadJsonUseCase
import br.com.akgs.doevida.infra.NotificationManager
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authService: FirebaseAuthService,
    private val firebaseDatabaseService: FirebaseDatabaseService,
    private val readJson: ReadJsonUseCase,
    private val notificationManager: NotificationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<ProfileAction>()
    val actions = _actions.asSharedFlow()


    val user = authService.currentUser()

    init {
        fetchUserData()
        loadEstados()
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnDismiss -> onDismiss()
            is ProfileAction.OnLougout -> onLogout()
            ProfileAction.NavigateToLogin -> {
                _uiState.value = _uiState.value.copy(
                    navigateToLogin = true
                )
            }

            ProfileAction.NavigateToMyDonation -> emitAction(ProfileAction.NavigateToMyDonation)
            ProfileAction.NavigateToInformacion -> emitAction(ProfileAction.NavigateToInformacion)
            ProfileAction.OnEditMode -> onEditMode()
            is ProfileAction.OnUpdatePhone -> onUpdatePhone(action.phone)
            is ProfileAction.OnUpdateBloodType -> onUpdateBloodType(action.bloodType)
            is ProfileAction.OnUpdateCity -> onUpdateCity(action.city)
            is ProfileAction.OnUpdateState -> onUpdateState(action.state)
            ProfileAction.OnSaveUserInfo -> onSaveInfo()
            ProfileAction.OnToggleEstadoDropdown -> onToggleEstadoDropdown()
            ProfileAction.OnToggleCidadeDropdown -> onToggleCidadeDropdown()
            ProfileAction.OnBackClick -> onBack()
            ProfileAction.OnShowTermos -> onShowTermos()
        }
    }

    private fun onShowTermos() {
        _uiState.value = _uiState.value.copy(
            showTermos = true
        )
    }

    fun updateUserBloodType(bloodType: String) {
        viewModelScope.launch {
            try {
                _uiState.value.user?.bloodType?.let { oldType ->
                    notificationManager.unsubscribeFromBloodType(oldType)
                }
                notificationManager.subscribeToBloodType(bloodType)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun onBack() {
        if (_uiState.value.isEditMode) {
            if (hasUnsavedChanges()) {
                _uiState.value = _uiState.value.copy(
                    showDiscardChangesDialog = true
                )
            } else {
                onDismiss()
            }
        } else {
            emitAction(ProfileAction.OnBackClick)
        }
    }

    private fun hasUnsavedChanges(): Boolean {
        val state = _uiState.value
        return state.editPhone.isNotEmpty() ||
                state.editBloodType.isNotEmpty() ||
                state.editCity.isNotEmpty() ||
                state.editState.isNotEmpty()

    }

    private fun onToggleCidadeDropdown() {
        _uiState.value = _uiState.value.copy(
            isCidadeDropdownExpanded = !_uiState.value.isCidadeDropdownExpanded,
            isEstadoDropdownExpanded = false
        )
    }

    private fun onToggleEstadoDropdown() {
        _uiState.value = _uiState.value.copy(
            isEstadoDropdownExpanded = !_uiState.value.isEstadoDropdownExpanded,
            isCidadeDropdownExpanded = false
        )
    }

    private fun onSaveInfo() {
        val updatedUser = user.copy(
            phone = _uiState.value.editPhone.takeIf { it.isNotEmpty() } ?: user.phone,
            bloodType = _uiState.value.editBloodType.takeIf { it.isNotEmpty() } ?: user.bloodType,
            city = _uiState.value.editCity.takeIf { it.isNotEmpty() } ?: user.city,
            state = _uiState.value.editState.takeIf { it.isNotEmpty() } ?: user.state
        )


        firebaseDatabaseService.updateUser(updatedUser)
        updateUserBloodType(updatedUser.bloodType ?: "")

        _uiState.value = _uiState.value.copy(
            user = updatedUser,
            isEditMode = false,
            editPhone = "",
            editBloodType = "",
            editCity = "",
            editState = ""
        )
    }

    private fun onUpdateCity(city: String) {
        _uiState.value = _uiState.value.copy(
            editCity = city,
            isCidadeDropdownExpanded = false
        )
    }

    private fun onUpdateState(state: String) {
        _uiState.value = _uiState.value.copy(
            editState = state,
            isEstadoDropdownExpanded = false
        )
        loadCidades(state)
    }

    private fun loadCidades(estado: String) {
        viewModelScope.launch {
            val cidades = readJson.getCidadesByEstado(estado)
            _uiState.value = _uiState.value.copy(
                cities = cidades
            )
        }
    }

    private fun loadEstados() {
        viewModelScope.launch {
            val estados = readJson.readJsonEstados().map { it.sigla }
            _uiState.value = _uiState.value.copy(
                states = estados
            )
        }
    }


    private fun onUpdateBloodType(bloodType: String) {
        _uiState.value = _uiState.value.copy(
            editBloodType = bloodType
        )
    }

    private fun onUpdatePhone(phone: String) {
        _uiState.value = _uiState.value.copy(
            editPhone = phone
        )
    }

    private fun onEditMode() {
        _uiState.value = _uiState.value.copy(
            isEditMode = true,
            editPhone = user.phone ?: "",
            editBloodType = user.bloodType ?: "",
            editCity = user.city,
            editState = user.state,
        )
    }

    private fun onDismiss() {
        _uiState.value = _uiState.value.copy(
            isEditMode = false,
            editPhone = "",
            editBloodType = "",
            editCity = "",
            editState = "",
            isEstadoDropdownExpanded = false,
            isCidadeDropdownExpanded = false,
            showTermos = false,
        )
    }

    private fun emitAction(action: ProfileAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun onLogout() {
        authService.signOut()
        emitAction(ProfileAction.OnLougout)
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
}
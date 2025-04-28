package br.com.akgs.doevida.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.ui.donation.SolicitationAction
import br.com.akgs.doevida.ui.home.HomeAction
import br.com.akgs.doevida.ui.home.HomeState
import br.com.akgs.doevida.ui.login.LoginAction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authService: FirebaseAuthService,
    private val firebaseDatabaseService: FirebaseDatabaseService,
)  : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<ProfileAction>()
    val actions = _actions.asSharedFlow()


    val user = authService.currentUser()
    init {
        fetchUserData()
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnSave -> onSave(action.donation)
            is ProfileAction.OnDismiss -> {}
            is ProfileAction.OnShowAdd -> onShowAdd()
            is ProfileAction.OnLougout -> onLogout()
            ProfileAction.NavigateToLogin -> {
                _uiState.value = _uiState.value.copy(
                    navigateToLogin = true
                )
            }

        }
    }

    private fun emitAction(action: ProfileAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun onLogout() {
        authService.signOut()
        emitAction(ProfileAction.NavigateToLogin)
    }

    private fun onShowAdd() {
        TODO("Not yet implemented")
    }

    private fun onSave(donation: Donation) {
        TODO("Not yet implemented")
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
package br.com.akgs.doevida.ui.donation.requestDonation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.akgs.doevida.domain.usecases.ReadJsonUseCase
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.infra.remote.entities.RequestDonation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RequestDonationViewModel(
    private val readJsonUseCase: ReadJsonUseCase,
    private val firebaseDatabaseService: FirebaseDatabaseService,
    private val firebaseAuthService: FirebaseAuthService,

    ) : ViewModel() {
    private val _requestDonationState = MutableStateFlow(RequestDonationState())
    val requestDonationState = _requestDonationState.asStateFlow()

    val user = firebaseAuthService.currentUser().id

    init {
//        getSolicitationsByCity(user?.state, user?.city)
        loadEstados()
    }



    fun onAction(action: RequestDonationAction) {
        when (action) {
            is RequestDonationAction.OnNameChange -> onNameChange(action.name)
            is RequestDonationAction.OnPhoneChange -> onPhoneChange(action.phone)
            is RequestDonationAction.OnLocalChange -> onLocalChange(action.local)
            is RequestDonationAction.OnEstadoChange -> onEstadoChange(action.estado)
            is RequestDonationAction.OnCidadeChange -> onCidadeChange(action.cidade)
            is RequestDonationAction.OnTipoSanguineoChange -> onTipoSanguineoChange(action.bloodType)
            is RequestDonationAction.OnTipoPedidoChange -> onTipoPedido(action.type)
            RequestDonationAction.OnSaveClick -> onRequestClick()
        }
    }

    private fun onTipoPedido(type: String) {
        _requestDonationState.value = _requestDonationState.value.copy(
            tipoPedido = type
        )

    }

    private fun onLocalChange(local: String) {
        _requestDonationState.value = _requestDonationState.value.copy(
            local = local
        )
    }

    private fun onRequestClick() {
        viewModelScope.launch {
            val requestDonation = RequestDonation(
                name = _requestDonationState.value.name,
                phone = _requestDonationState.value.phone,
                state = _requestDonationState.value.estado,
                city = _requestDonationState.value.cidade,
                bloodType = _requestDonationState.value.tipoSanguineo,
                local = _requestDonationState.value.local,
                userId = user,
                status = "Pendente",
                id = "",
            )
            firebaseDatabaseService.createRequestDonation(requestDonation) { success, error ->
                if (success) {
                    // Handle success
                    println("Solicitação de doação criada com sucesso!").toString()
                    _requestDonationState.value = _requestDonationState.value.copy(
//                    message = "Solicitação de doação criada com sucesso!"

                        navigateToHome = true
                    )
                    onAction(RequestDonationAction.OnSaveSuccess)
                } else if (error != null) {
                    // Handle error
                    println("Erro ao criar solicitação: ${error}").toString()
//                _requestDonationState.value = _requestDonationState.value.copy(
//                    message = "Erro ao criar solicitação: ${error.message}"
//                )
                } else {
                    // Handle error
                }
            }
        }
    }

    private fun getSolicitationsByCity(state: String?, city: String?) {
        if (state != null) {
            if (city != null) {
                firebaseDatabaseService.getSolicitationsByCity(state, city) { donations, error ->
                    if (error != null) {
                        // Handle error
                        _requestDonationState.value = _requestDonationState.value.copy(
                            solitacoes = donations ?: emptyList(),
                        )
                        println("Error fetching solicitations: $error")
                    } else {
                        // Handle success
                        println("Solicitations fetched successfully: $donations")
                    }
                }
            }
        }
    }


    private fun onEstadoChange(estado: String) {
        _requestDonationState.value = _requestDonationState.value.copy(
            estado = estado
        )
        val cidadesFiltradas = readJsonUseCase.getCidadesByEstado(estado)
        _requestDonationState.value = _requestDonationState.value.copy(cidades = cidadesFiltradas)
    }

    fun loadEstados() {
        val estadosJson = readJsonUseCase.readJsonEstados().map { it.sigla }
        _requestDonationState.value = _requestDonationState.value.copy(
            estados = estadosJson
        )
    }


    private fun onTipoSanguineoChange(tipoSanguineo: String) {
        _requestDonationState.value = _requestDonationState.value.copy(
            tipoSanguineo = tipoSanguineo
        )
    }


    private fun onCidadeChange(city: String) {
        _requestDonationState.value = _requestDonationState.value.copy(
            cidade = city
        )

    }

    private fun onPhoneChange(phone: String) {
        _requestDonationState.value = _requestDonationState.value.copy(
            phone = phone
        )
    }

    private fun onNameChange(name: String) {
        _requestDonationState.value = _requestDonationState.value.copy(
            name = name
        )
    }


}
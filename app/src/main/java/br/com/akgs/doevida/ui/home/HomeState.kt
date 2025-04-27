package br.com.akgs.doevida.ui.home

import br.com.akgs.doevida.infra.remote.entities.User

data class HomeState(
    var navigateToSolicitation: Boolean = false,
    val user: User? = null,
)
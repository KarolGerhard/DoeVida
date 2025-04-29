package br.com.akgs.doevida.ui.home

import br.com.akgs.doevida.infra.remote.entities.User
import br.com.akgs.doevida.ui.Notifications.Notifications

data class HomeState(
    var navigateToSolicitation: Boolean = false,
    val user: User? = null,
    val donationsCount: Int = 0,
    val showNotifications: Boolean = false,
    val unreadNotifications: Int = 0,
    val notifications: List<Notifications> = emptyList(),
    val isLoading: Boolean = false,
)
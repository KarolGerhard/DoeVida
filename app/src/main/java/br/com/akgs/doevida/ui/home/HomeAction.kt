package br.com.akgs.doevida.ui.home

interface HomeAction {
    data class ShowError(val message: String) : HomeAction
    object NavigateToSolicitation : HomeAction
    object NavigateToHemocentros : HomeAction
    data object OnNotificationClick : HomeAction
    data object OnNotificationDismiss : HomeAction

}
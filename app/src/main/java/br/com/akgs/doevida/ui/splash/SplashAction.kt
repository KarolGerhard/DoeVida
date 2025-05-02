package br.com.akgs.doevida.ui.splash

interface SplashAction {
    data object OnNavigateToAuth : SplashAction
    data object OnNavigateToHome : SplashAction

}
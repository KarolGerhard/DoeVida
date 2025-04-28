package br.com.akgs.doevida.ui.splash

data class SplashState (
    val isLoading: Boolean = true,
    val isAuthenticated: Boolean = false,
    val error: String? = null,
    val navigateToAuth: Boolean = false,
    val navigateToHome: Boolean = false,
)

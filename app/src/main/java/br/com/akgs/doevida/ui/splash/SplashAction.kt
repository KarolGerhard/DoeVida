package br.com.akgs.doevida.ui.splash

import br.com.akgs.doevida.infra.remote.entities.Donation
import br.com.akgs.doevida.infra.remote.entities.RequestDonation

interface SplashAction {
    data object OnNavigateToAuth : SplashAction
    data object OnNavigateToHome : SplashAction

}
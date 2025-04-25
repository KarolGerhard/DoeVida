package br.com.akgs.doevida.ui.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import br.com.akgs.doevida.ui.donation.RequestDonationViewModel
import br.com.akgs.doevida.ui.donation.SolicitationViewModel
import br.com.akgs.doevida.ui.donation.SolicitationScreen
import org.koin.dsl.module

val donationModule = module {
     viewModelOf(::RequestDonationViewModel)
     viewModelOf(::SolicitationViewModel)
    // viewModelOf(::DonationListViewModel)
}
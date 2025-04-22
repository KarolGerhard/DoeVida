package br.com.akgs.doevida.ui.di

import br.com.akgs.doevida.ui.login.LoginViewModel
import br.com.akgs.doevida.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}
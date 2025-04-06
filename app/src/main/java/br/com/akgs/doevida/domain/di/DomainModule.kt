package br.com.akgs.doevida.domain.di

import br.com.akgs.doevida.domain.usecases.UserUseCase
import br.com.akgs.doevida.domain.usecases.UserUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<UserUseCase> {
        UserUseCaseImpl(
            userRepository = get(),
        )
    }
    factory {
        UserUseCaseImpl(
            userRepository = get(),
        )
    }
}
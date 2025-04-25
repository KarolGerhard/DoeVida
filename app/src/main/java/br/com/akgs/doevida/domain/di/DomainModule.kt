package br.com.akgs.doevida.domain.di

import br.com.akgs.doevida.domain.usecases.AuthUseCase
import br.com.akgs.doevida.domain.usecases.AuthUseCaseImpl
import br.com.akgs.doevida.domain.usecases.DonationUseCase
import br.com.akgs.doevida.domain.usecases.DonationUseCaseImpl
import br.com.akgs.doevida.domain.usecases.ReadJsonUseCase
import br.com.akgs.doevida.domain.usecases.ReadJsonUseCaseImpl
import br.com.akgs.doevida.domain.usecases.UserUseCase
import br.com.akgs.doevida.domain.usecases.UserUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<UserUseCase> {
        UserUseCaseImpl(
            userRepository = get(),
        )
    }
    factory<AuthUseCase> {
        AuthUseCaseImpl(
            authService = get(),
        )
    }
    factory<DonationUseCase> {
        DonationUseCaseImpl(
            donationRepository = get(),
            firebaseDatabaseService = get(),
        )
    }
    factory<ReadJsonUseCase> { ReadJsonUseCaseImpl(
        readJson = get(),

    ) }

}
package br.com.akgs.doevida.infra.di

import br.com.akgs.doevida.infra.ReadJson
import br.com.akgs.doevida.infra.remote.FirebasDatabaseServiceImpl
import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.FirebaseAuthServiceImpl
import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import org.koin.dsl.module

val infraModule = module {
    single<FirebaseAuthService> { FirebaseAuthServiceImpl() }
    single<FirebaseDatabaseService> { FirebasDatabaseServiceImpl() }
    single<ReadJson> {
        ReadJson(
            context = get(),
        )
    }
}
package br.com.akgs.doevida

import android.app.Application
import br.com.akgs.doevida.domain.di.domainModule
import br.com.akgs.doevida.infra.di.infraModule
import br.com.akgs.doevida.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            // modules
            modules(
                infraModule + domainModule + uiModule
            )
        }
    }
}
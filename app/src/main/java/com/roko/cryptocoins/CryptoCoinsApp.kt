package com.roko.cryptocoins

import android.app.Application
import com.roko.cryptocoins.di.appModule
import com.roko.cryptocoins.di.databaseModule
import com.roko.cryptocoins.di.networkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CryptoCoinsApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoCoinsApp)
            androidLogger()

            modules(
                appModule,
                networkModule,
                databaseModule
            )

        }
    }
}
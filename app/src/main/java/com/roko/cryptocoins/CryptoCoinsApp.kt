package com.roko.cryptocoins

import android.app.Application
import com.roko.cryptocoins.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CryptoCoinsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoCoinsApp)
            androidLogger()

            modules(
                appModule
            )

        }
    }
}
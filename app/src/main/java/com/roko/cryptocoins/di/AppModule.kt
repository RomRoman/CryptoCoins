package com.roko.cryptocoins.di

import com.roko.cryptocoins.CryptoCoinsApp
import com.roko.cryptocoins.crypto.data.CoinRepositoryImpl
import com.roko.cryptocoins.crypto.domain.CoinRepository
import com.roko.cryptocoins.crypto.presentation.coin_list.CoinListViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    // Injected application-wide coroutine scope
    single<CoroutineScope> {
        (androidApplication() as CryptoCoinsApp).applicationScope
    }

    singleOf(::CoinRepositoryImpl).bind<CoinRepository>()


    viewModelOf(::CoinListViewModel)
}
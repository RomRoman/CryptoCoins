package com.roko.cryptocoins.di

import com.roko.cryptocoins.core.data.networking.HttpClientFactory
import com.roko.cryptocoins.crypto.data.networking.RemoteCoinDataSource
import com.roko.cryptocoins.crypto.domain.CoinDataSource
import com.roko.cryptocoins.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClientFactory.create(CIO.create())
    }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}
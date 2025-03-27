package com.roko.cryptocoins.di

import com.roko.cryptocoins.core.data.networking.HttpClientFactory
import com.roko.cryptocoins.crypto.data.networking.KtorRemoteCoinDataSource
import com.roko.cryptocoins.crypto.domain.RemoteCoinDataSource
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {

    single {
        HttpClientFactory.create(CIO.create())
    }

    singleOf(::KtorRemoteCoinDataSource).bind<RemoteCoinDataSource>()
}
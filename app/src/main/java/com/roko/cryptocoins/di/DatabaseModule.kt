package com.roko.cryptocoins.di

import androidx.room.Room
import com.roko.cryptocoins.core.data.database.CoinDatabase
import com.roko.cryptocoins.crypto.data.database.RoomLocalCoinDataSource
import com.roko.cryptocoins.crypto.domain.LocalCoinDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            CoinDatabase::class.java,
            "coins.db"
        ).build()
    }

    single { get<CoinDatabase>().coinDao }

    singleOf(::RoomLocalCoinDataSource).bind<LocalCoinDataSource>()

}
package com.roko.cryptocoins.crypto.domain

import com.roko.cryptocoins.core.domain.util.NetworkError
import com.roko.cryptocoins.core.domain.util.Result
import java.time.ZonedDateTime

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>


}
package com.roko.cryptocoins.crypto.domain

import com.roko.cryptocoins.core.domain.util.DataError
import com.roko.cryptocoins.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias CoinId = String

interface LocalCoinDataSource {
    fun getCoins(): Flow<List<Coin>>
    suspend fun upsertCoins(coins: List<Coin>): Result<List<CoinId>, DataError>
    suspend fun deleteAllCoins()
}
package com.roko.cryptocoins.crypto.domain

import com.roko.cryptocoins.core.domain.util.EmptyDataResult
import com.roko.cryptocoins.core.domain.util.Error
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoins(): Flow<List<Coin>>
    suspend fun fetchCoins(): EmptyDataResult<Error>
    suspend fun refreshCoins(): EmptyDataResult<Error>
    suspend fun deleteCoins()
}
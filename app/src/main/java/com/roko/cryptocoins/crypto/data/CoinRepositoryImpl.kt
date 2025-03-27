package com.roko.cryptocoins.crypto.data

import com.roko.cryptocoins.core.domain.util.EmptyDataResult
import com.roko.cryptocoins.core.domain.util.Error
import com.roko.cryptocoins.core.domain.util.Result
import com.roko.cryptocoins.core.domain.util.asEmptyDataResult
import com.roko.cryptocoins.crypto.domain.Coin
import com.roko.cryptocoins.crypto.domain.CoinRepository
import com.roko.cryptocoins.crypto.domain.LocalCoinDataSource
import com.roko.cryptocoins.crypto.domain.RemoteCoinDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class CoinRepositoryImpl(
    private val localCoinDataSource: LocalCoinDataSource,
    private val remoteCoinDataSource: RemoteCoinDataSource,
    private val applicationScope: CoroutineScope
): CoinRepository {

    companion object {
        const val REFRESH_THRESHOLD_MILLIS = 60_000L
    }

    private var lastFetchedTimeMillis = 0L
    private val isTimeToRefresh: Boolean
        get() = System.currentTimeMillis() - lastFetchedTimeMillis > REFRESH_THRESHOLD_MILLIS

    override fun getCoins(): Flow<List<Coin>> {
        return localCoinDataSource.getCoins()
    }

    override suspend fun fetchCoins(): EmptyDataResult<Error> {
        return when(val result = remoteCoinDataSource.getCoins()) {
            is Result.Failure -> result.asEmptyDataResult()
            is Result.Success -> {
                lastFetchedTimeMillis = System.currentTimeMillis()
                applicationScope.async {
                    localCoinDataSource.upsertCoins(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun refreshCoins(): EmptyDataResult<Error> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCoins() {
        TODO("Not yet implemented")
    }

}
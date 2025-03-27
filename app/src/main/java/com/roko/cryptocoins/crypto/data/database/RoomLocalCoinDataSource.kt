package com.roko.cryptocoins.crypto.data.database

import android.database.sqlite.SQLiteFullException
import com.roko.cryptocoins.core.data.database.CoinDao
import com.roko.cryptocoins.core.domain.util.DataError
import com.roko.cryptocoins.core.domain.util.Result
import com.roko.cryptocoins.crypto.data.mappers.toCoin
import com.roko.cryptocoins.crypto.data.mappers.toCoinEntity
import com.roko.cryptocoins.crypto.domain.Coin
import com.roko.cryptocoins.crypto.domain.CoinId
import com.roko.cryptocoins.crypto.domain.LocalCoinDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalCoinDataSource(
    private val coinDao: CoinDao
): LocalCoinDataSource {

    override fun getCoins(): Flow<List<Coin>> {
        return coinDao.getCoins()
            .map { coinEntities ->
                coinEntities.map { it.toCoin() }
            }
    }

    override suspend fun upsertCoins(coins: List<Coin>): Result<List<CoinId>, DataError> {
        return try {
            val entities = coins.map { it.toCoinEntity() }
            coinDao.upsertCoins(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Failure(DataError.DISK_FULL)
        }
    }

    override suspend fun deleteAllCoins() {
        coinDao.deleteAllCoins()
    }


}
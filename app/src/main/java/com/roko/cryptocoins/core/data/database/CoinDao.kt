package com.roko.cryptocoins.core.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Upsert
    suspend fun upsertCoins(coins: List<CoinEntity>)

    @Query("SELECT * FROM coinentity ORDER BY rank")
    fun getCoins(): Flow<List<CoinEntity>>

    @Query("DELETE FROM coinentity")
    suspend fun deleteAllCoins()

}
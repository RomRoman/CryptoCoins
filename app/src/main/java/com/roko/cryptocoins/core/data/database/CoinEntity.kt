package com.roko.cryptocoins.core.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoinEntity(
    @PrimaryKey
    val id: String,
    val rank: Int,
    val symbol: String,
    val name: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double,
    val favorite: Boolean = false,
    val pinned: Boolean = false
)

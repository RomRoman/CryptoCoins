package com.roko.cryptocoins.crypto.data.mappers

import com.roko.cryptocoins.core.data.database.CoinEntity
import com.roko.cryptocoins.crypto.domain.Coin

fun CoinEntity.toCoin() = Coin(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    marketCapUsd = marketCapUsd,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr,
)

fun Coin.toCoinEntity() = CoinEntity(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    marketCapUsd = marketCapUsd,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr,
)
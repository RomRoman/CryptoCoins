package com.roko.cryptocoins.crypto.data.mappers

import com.roko.cryptocoins.crypto.data.networking.dto.CoinDto
import com.roko.cryptocoins.crypto.data.networking.dto.CoinPriceDto
import com.roko.cryptocoins.crypto.domain.Coin
import com.roko.cryptocoins.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin() = Coin(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    marketCapUsd = marketCapUsd,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr
)

fun CoinPriceDto.toCoinPrice() = CoinPrice(
    priceUsd = priceUsd,
    dateTime = Instant
        .ofEpochMilli(time)
        .atZone(ZoneId.of("UTC"))
)
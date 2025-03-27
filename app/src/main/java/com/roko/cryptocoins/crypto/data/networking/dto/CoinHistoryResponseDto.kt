package com.roko.cryptocoins.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryResponseDto(
    val data: List<CoinPriceDto>
)
package com.roko.cryptocoins.crypto.data.networking

import com.roko.cryptocoins.core.data.networking.constructUrl
import com.roko.cryptocoins.core.data.networking.safeCall
import com.roko.cryptocoins.core.domain.util.NetworkError
import com.roko.cryptocoins.core.domain.util.Result
import com.roko.cryptocoins.core.domain.util.map
import com.roko.cryptocoins.crypto.data.mappers.toCoin
import com.roko.cryptocoins.crypto.data.mappers.toCoinPrice
import com.roko.cryptocoins.crypto.data.networking.dto.CoinHistoryResponseDto
import com.roko.cryptocoins.crypto.data.networking.dto.CoinsResponseDto
import com.roko.cryptocoins.crypto.domain.Coin
import com.roko.cryptocoins.crypto.domain.RemoteCoinDataSource
import com.roko.cryptocoins.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class KtorRemoteCoinDataSource(
   private val httpClient: HttpClient
) : RemoteCoinDataSource {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { coinDto ->
                coinDto.toCoin()
            }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return safeCall<CoinHistoryResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { coinPriceDto ->
                coinPriceDto.toCoinPrice()
            }
        }
    }
}
package com.roko.cryptocoins.crypto.data.networking

import com.roko.cryptocoins.core.data.networking.constructUrl
import com.roko.cryptocoins.core.data.networking.safeCall
import com.roko.cryptocoins.core.domain.util.NetworkError
import com.roko.cryptocoins.core.domain.util.Result
import com.roko.cryptocoins.core.domain.util.map
import com.roko.cryptocoins.crypto.data.mappers.toCoin
import com.roko.cryptocoins.crypto.data.networking.dto.CoinsResponseDto
import com.roko.cryptocoins.crypto.domain.Coin
import com.roko.cryptocoins.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
   private val httpClient: HttpClient
) : CoinDataSource {

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
}
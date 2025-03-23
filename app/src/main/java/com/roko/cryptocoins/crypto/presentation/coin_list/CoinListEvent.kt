package com.roko.cryptocoins.crypto.presentation.coin_list

import com.roko.cryptocoins.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}
package com.roko.cryptocoins.crypto.presentation.coin_list

import com.roko.cryptocoins.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
    data object OnRefresh: CoinListAction
}

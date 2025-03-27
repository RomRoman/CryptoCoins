package com.roko.cryptocoins.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.roko.cryptocoins.crypto.presentation.coin_detail.chart.DataPoint

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCap: DisplayableCurrency,
    val price: DisplayableCurrency,
    val changePercentage24h: DisplayableNumber,
    @DrawableRes val iconRes: Int,
    val coinPriceHistory: List<DataPoint> = emptyList(),
)

data class DisplayableCurrency(
    val value: Double,
    val formattedValue: String,
    val currencySymbol: String = "$",
    val currencyFactor: Double = 1.0,
) {
    val formattedCurrency: String
        get() = "$currencySymbol $formattedValue"
}

data class DisplayableNumber(
    val value: Double,
    val formattedValue: String,
)



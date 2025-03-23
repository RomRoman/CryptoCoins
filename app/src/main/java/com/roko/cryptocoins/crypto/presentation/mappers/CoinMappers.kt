package com.roko.cryptocoins.crypto.presentation.mappers

import com.roko.cryptocoins.crypto.domain.Coin
import com.roko.cryptocoins.crypto.presentation.models.CoinUi
import com.roko.cryptocoins.crypto.presentation.models.DisplayableCurrency
import com.roko.cryptocoins.crypto.presentation.models.DisplayableNumber
import com.roko.cryptocoins.core.presentation.util.getDrawableIdForCoin
import java.text.NumberFormat
import java.util.Locale

fun Coin.toCoinUi() = CoinUi(
    id = id,
    rank = rank,
    name = name,
    symbol = symbol,
    price = priceUsd.toDisplayableCurrency(),
    marketCap = marketCapUsd.toDisplayableCurrency(),
    changePercentage24h = changePercent24Hr.toDisplayableNumber(),
    iconRes = getDrawableIdForCoin(symbol)

)

fun Double.toDisplayableCurrency() = DisplayableCurrency(
    value = this,
    formattedValue = twoFractionDigitsFormatter().format(this),
)

fun Double.toDisplayableNumber() = DisplayableNumber(
    value = this,
    formattedValue = twoFractionDigitsFormatter().format(this)
)

private fun twoFractionDigitsFormatter(
    locale: Locale = Locale.getDefault()
) = NumberFormat.getNumberInstance(locale).apply {
    minimumFractionDigits = 2
    maximumFractionDigits = 2
}
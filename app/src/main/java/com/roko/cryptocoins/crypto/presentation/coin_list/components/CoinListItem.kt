package com.roko.cryptocoins.crypto.presentation.coin_list.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.roko.cryptocoins.crypto.domain.Coin
import com.roko.cryptocoins.crypto.presentation.mappers.toCoinUi
import com.roko.cryptocoins.crypto.presentation.models.CoinUi
import com.roko.cryptocoins.ui.theme.CryptoCoinsTheme

@Composable
fun CoinListItem(
    coinUi: CoinUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
            .then(
                when(configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> Modifier
                        .padding(horizontal = WindowInsets
                            .statusBars
                            .asPaddingValues()
                            .calculateTopPadding()
                        )
                    else -> Modifier
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = coinUi.iconRes),
            contentDescription = coinUi.name,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(85.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = coinUi.symbol,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = coinUi.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${coinUi.price.formattedCurrency}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            PriceChange(
                change = coinUi.changePercentage24h,
            )
        }


    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
private fun CoinListItemPreview() {
    CryptoCoinsTheme(
        dynamicColor = true,
    ) {
        CoinListItem(
            coinUi = previewCoin,
            onClick = {},
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}

internal val previewCoin = Coin(
    id = "crypto_coin",
    rank = 1,
    name = "Bitcoin",
    symbol = "BTC",
    marketCapUsd = 4675846392.0,
    priceUsd = 862354.9678,
    changePercent24Hr = -10.23,
).toCoinUi()
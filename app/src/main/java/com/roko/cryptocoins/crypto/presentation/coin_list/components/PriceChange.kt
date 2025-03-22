package com.roko.cryptocoins.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.roko.cryptocoins.crypto.presentation.models.DisplayableNumber
import com.roko.cryptocoins.ui.theme.CryptoCoinsTheme
import com.roko.cryptocoins.ui.theme.greenPercentageContainer
import com.roko.cryptocoins.ui.theme.greenPercentageContent

@Composable
fun PriceChange(
    change: DisplayableNumber,
    modifier: Modifier = Modifier
) {

    val containerColor = if (change.value > 0) {
        greenPercentageContainer
    } else {
        MaterialTheme.colorScheme.errorContainer
    }
    val contentColor = if (change.value > 0) {
        greenPercentageContent
    } else {
        MaterialTheme.colorScheme.onErrorContainer
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100f))
            .background(containerColor)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (change.value > 0) {
                if (change.value <= 10)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardDoubleArrowUp

            } else {
                if (change.value >= - 10)
                    Icons.Filled.KeyboardArrowDown
                else
                    Icons.Filled.KeyboardDoubleArrowDown
            },
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = contentColor
        )
        Text(
            text = "${change.formattedValue} %",
            style = MaterialTheme.typography.labelMedium,
            color = contentColor,
        )

    }

}

@PreviewLightDark
@Composable
private fun PriceChangePreview() {
    CryptoCoinsTheme {
        PriceChange(
            change = DisplayableNumber(
                value = 19.5467,
                formattedValue = "19.54"
            )
        )
    }
}
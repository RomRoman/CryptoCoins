@file:OptIn(ExperimentalLayoutApi::class)

package com.roko.cryptocoins.crypto.presentation.coin_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.roko.cryptocoins.R
import com.roko.cryptocoins.crypto.presentation.coin_detail.chart.ChartStyle
import com.roko.cryptocoins.crypto.presentation.coin_detail.chart.DataPoint
import com.roko.cryptocoins.crypto.presentation.coin_detail.chart.LineChart
import com.roko.cryptocoins.crypto.presentation.coin_detail.components.InfoCard
import com.roko.cryptocoins.crypto.presentation.coin_list.CoinListState
import com.roko.cryptocoins.crypto.presentation.coin_list.CoinListViewModel
import com.roko.cryptocoins.crypto.presentation.coin_list.components.previewCoin
import com.roko.cryptocoins.crypto.presentation.mappers.toDisplayableCurrency
import com.roko.cryptocoins.ui.theme.CryptoCoinsTheme
import com.roko.cryptocoins.ui.theme.greenPercentageContent
import com.roko.cryptocoins.ui.theme.greenTrendingContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinDetailScreenRoot(
    viewModel: CoinListViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CoinDetailScreen(
        state = state,
//        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
private fun CoinDetailScreen(
    state: CoinListState,
//    onAction: (CoinDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        LoadingState()
    } else if (state.selectedCoin != null) {
       val coin = state.selectedCoin
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.iconRes),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary

            )
            Text(
                text = coin.name,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = coin.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                InfoCard(
                    title = stringResource(id = R.string.market_cap),
                    formattedText = coin.marketCap.formattedCurrency,
                    icon = ImageVector.vectorResource(id = R.drawable.stock)
                )
                InfoCard(
                    title = stringResource(id = R.string.price),
                    formattedText = coin.price.formattedCurrency,
                    icon = ImageVector.vectorResource(id = R.drawable.dollar)
                )
                val absoluteChangeFormatted =
                    (coin.price.value * (coin.changePercentage24h.value / 100))
                        .toDisplayableCurrency()
                val isPositive = coin.changePercentage24h.value > 0
                val contentColor = if (isPositive) {
                   if(isSystemInDarkTheme()) greenPercentageContent else greenTrendingContent
                } else {
                    MaterialTheme.colorScheme.error
                }
                InfoCard(
                    title = stringResource(id = R.string.change_last_24h),
                    formattedText = absoluteChangeFormatted.formattedCurrency,
                    icon = ImageVector.vectorResource(id =
                        if (isPositive) {
                            R.drawable.trending
                        } else {
                            R.drawable.trending_down
                        }
                    ),
                    contentColor = contentColor
                )
            }

            AnimatedVisibility(
                visible = coin.coinPriceHistory.isNotEmpty()
            ) {
                var selectedDataPoint by remember {
                    mutableStateOf<DataPoint?>(null)
                }
                var labelWidth by remember {
                    mutableFloatStateOf(0f)
                }
                var totalChartWidth by remember {
                    mutableFloatStateOf(0f)
                }
                val amountOfVisibleDataPoints = if (labelWidth > 0) {
                    ((totalChartWidth - 2.5*labelWidth) / labelWidth).toInt()
                } else {
                    0
                }
                val startIndex = (coin.coinPriceHistory.lastIndex - amountOfVisibleDataPoints).coerceAtLeast(0)
                LineChart(
                    dataPoints = coin.coinPriceHistory,
                    style = ChartStyle(
                        chartLineColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.3f
                        ),
                        selectedColor = MaterialTheme.colorScheme.primary,
                        helperLinesThicknessPx = 5f,
                        axisLinesThicknessPx = 5f,
                        labelFontSize = 14.sp,
                        minYLabelSpacing = 25.dp,
                        verticalPadding = 8.dp,
                        horizontalPadding = 8.dp,
                        xAxisLabelSpacing = 8.dp
                    ),
                    visibleDataPointsIndices = startIndex..coin.coinPriceHistory.lastIndex,
                    unit = "$",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .onSizeChanged { totalChartWidth = it.width.toFloat() },
                    selectedDataPoint = selectedDataPoint,
                    onSelectedDataPoint = {
                        selectedDataPoint = it
                    },
                    onXLabelWidthChange = {
                        labelWidth = it
                    }
                )
            }

        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

}

@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview() {
    CryptoCoinsTheme {
        CoinDetailScreen(
            state = CoinListState(
                selectedCoin = previewCoin
            ),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}
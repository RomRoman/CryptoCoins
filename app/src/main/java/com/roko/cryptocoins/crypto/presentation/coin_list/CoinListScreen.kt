package com.roko.cryptocoins.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.roko.cryptocoins.crypto.presentation.coin_list.components.CoinListItem
import com.roko.cryptocoins.crypto.presentation.coin_list.components.previewCoin
import com.roko.cryptocoins.ui.theme.CryptoCoinsTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinListScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
) {
//    CoinListScreen(
//        state = viewModel.state,
//        onAction = viewModel::onAction,
//        modifier = modifier
//    )
}

@Composable
private fun CoinListScreen(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        LoadingState()
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.coins) {coinUi ->
                CoinListItem(
                    coinUi = coinUi,
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider()
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
private fun CoinListScreenPreview() {
    CryptoCoinsTheme {
        CoinListScreen(
            state = CoinListState(
                isLoading = false,
                coins = (1..10).map {
                    previewCoin.copy(id = it.toString())
                }
            ),
            onAction = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}
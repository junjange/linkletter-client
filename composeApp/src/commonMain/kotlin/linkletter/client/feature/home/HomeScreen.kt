package linkletter.client.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import linkletter.client.core.designsystem.utils.addFocusCleaner
import linkletter.client.feature.home.componets.HomeSearchBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .addFocusCleaner(focusManager),
    ) {
        HomeSearchBar(
            focusManager = focusManager,
            onSearch = { query ->
            },
        )
    }
}

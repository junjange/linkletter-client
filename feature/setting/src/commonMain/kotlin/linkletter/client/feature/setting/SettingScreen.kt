package linkletter.client.feature.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import linkletter.client.core.designsystem.components.LinkletterTopAppBar
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter_client.feature.setting.generated.resources.Res
import linkletter_client.feature.setting.generated.resources.title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = koinViewModel(),
) {
    Scaffold(
        containerColor = LinkletterTheme.colorScheme.background,
        modifier =
            modifier
                .systemBarsPadding()
                .fillMaxSize()
                .padding(bottom = 80.dp),
        topBar = {
            LinkletterTopAppBar(title = stringResource(Res.string.title))
        },
    ) { innerPadding ->
    }
}

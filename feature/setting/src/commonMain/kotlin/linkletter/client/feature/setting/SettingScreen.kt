package linkletter.client.feature.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import linkletter.client.core.designsystem.components.LinkletterTopAppBar
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.feature.setting.components.AlarmToggle
import linkletter.client.feature.setting.model.SettingEvent
import linkletter_client.feature.setting.generated.resources.Res
import linkletter_client.feature.setting.generated.resources.alarm_toggle_description
import linkletter_client.feature.setting.generated.resources.alarm_toggle_title
import linkletter_client.feature.setting.generated.resources.title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
        SettingContent(
            enabled = state.alarmEnabled,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            onToggle = { boolean -> viewModel.onEvent(SettingEvent.AlarmToggle(boolean)) },
        )
    }
}

@Composable
private fun SettingContent(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onToggle: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        AlarmToggle(
            title = stringResource(Res.string.alarm_toggle_title),
            description = stringResource(Res.string.alarm_toggle_description),
            checked = enabled,
            onCheckedChange = onToggle,
        )
    }
}

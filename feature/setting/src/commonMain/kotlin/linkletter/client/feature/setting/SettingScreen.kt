package linkletter.client.feature.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import linkletter.client.core.designsystem.components.LinkletterTopAppBar
import linkletter.client.core.designsystem.theme.LinkletterTheme
import linkletter.client.feature.setting.components.AlarmToggle
import linkletter.client.feature.setting.model.SettingEvent
import linkletter_client.feature.setting.generated.resources.Res
import linkletter_client.feature.setting.generated.resources.alarm_toggle_description
import linkletter_client.feature.setting.generated.resources.alarm_toggle_title
import linkletter_client.feature.setting.generated.resources.snackbar_notification_permission_denied
import linkletter_client.feature.setting.generated.resources.snackbar_notification_permission_denied_action
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

    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController =
        remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val permissionState = controller.getPermissionState(Permission.REMOTE_NOTIFICATION)
        if (permissionState != PermissionState.Granted) {
            viewModel.onEvent(SettingEvent.AlarmToggle(false))
        }
    }

    val notificationPermissionDenied =
        stringResource(Res.string.snackbar_notification_permission_denied)
    val notificationPermissionDeniedAction =
        stringResource(Res.string.snackbar_notification_permission_denied_action)

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
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = LinkletterTheme.colorScheme.onSurface,
                    contentColor = LinkletterTheme.colorScheme.surface,
                    actionColor = LinkletterTheme.colorScheme.primary,
                )
            }
        },
    ) { innerPadding ->
        SettingContent(
            enabled = state.alarmEnabled,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            onToggle = { shouldEnable ->
                coroutineScope.launch {
                    if (!shouldEnable) {
                        viewModel.onEvent(SettingEvent.AlarmToggle(false))
                        return@launch
                    }
                    try {
                        controller.providePermission(Permission.REMOTE_NOTIFICATION)
                        viewModel.onEvent(SettingEvent.AlarmToggle(true))
                    } catch (deniedAlways: DeniedAlwaysException) {
                        val result =
                            snackbarHostState.showSnackbar(
                                message = notificationPermissionDenied,
                                actionLabel = notificationPermissionDeniedAction,
                                duration = SnackbarDuration.Short,
                            )

                        if (result == SnackbarResult.ActionPerformed) {
                            controller.openAppSettings()
                        }
                    } catch (denied: DeniedException) {
                        snackbarHostState.showSnackbar(
                            message = notificationPermissionDenied,
                            duration = SnackbarDuration.Short,
                        )
                    } catch (e: RequestCanceledException) {
                        viewModel.onEvent(SettingEvent.AlarmToggle(true))
                    } catch (e: Throwable) {
                        snackbarHostState.showSnackbar(
                            message = "예상치 못한 오류가 발생했습니다",
                            duration = SnackbarDuration.Short,
                        )
                    }
                }
            },
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

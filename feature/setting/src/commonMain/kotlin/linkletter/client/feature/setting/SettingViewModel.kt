package linkletter.client.feature.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import linkletter.client.core.domain.usecase.GetAlarmEnabledFlowUseCase
import linkletter.client.core.domain.usecase.SetAlarmEnabledUseCase
import linkletter.client.feature.setting.model.SettingEffect
import linkletter.client.feature.setting.model.SettingEvent
import linkletter.client.feature.setting.model.SettingState

internal class SettingViewModel(
    getAlarmEnabledFlowUseCase: GetAlarmEnabledFlowUseCase,
    private val setAlarmEnabledUseCase: SetAlarmEnabledUseCase,
) : ViewModel() {
    val state: StateFlow<SettingState> =
        getAlarmEnabledFlowUseCase()
            .map { enabled ->
                SettingState(alarmEnabled = enabled)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = SettingState(alarmEnabled = false),
            )

    private val _effect = Channel<SettingEffect>(Channel.BUFFERED)
    val effect get() = _effect.receiveAsFlow()

    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.AlarmToggle -> toggleAlarm(enabled = event.enabled)
        }
    }

    fun toggleAlarm(enabled: Boolean) {
        viewModelScope.launch {
            setAlarmEnabledUseCase(enabled = enabled)
        }
    }
}

package linkletter.client.feature.setting.model

sealed interface SettingEvent {
    data class AlarmToggle(
        val enabled: Boolean,
    ) : SettingEvent
}

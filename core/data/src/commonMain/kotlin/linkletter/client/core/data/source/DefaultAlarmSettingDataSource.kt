package linkletter.client.core.data.source

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.datastore.AlarmSettingPreferences

internal class DefaultAlarmSettingDataSource(
    private val preferences: AlarmSettingPreferences,
) : AlarmSettingDataSource {
    override val getAlarmEnabledFlow: Flow<Boolean> = preferences.alarmEnabledFlow

    override suspend fun setAlarmEnabled(enabled: Boolean) = preferences.setAlarmEnabled(enabled = enabled)
}

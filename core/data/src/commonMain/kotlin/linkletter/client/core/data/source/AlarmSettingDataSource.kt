package linkletter.client.core.data.source

import kotlinx.coroutines.flow.Flow

interface AlarmSettingDataSource {
    val getAlarmEnabledFlow: Flow<Boolean>

    suspend fun setAlarmEnabled(enabled: Boolean)
}

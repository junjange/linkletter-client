package linkletter.client.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AlarmSettingRepository {
    val getAlarmEnabledFlow: Flow<Boolean>

    suspend fun setAlarmEnabled(enabled: Boolean)
}

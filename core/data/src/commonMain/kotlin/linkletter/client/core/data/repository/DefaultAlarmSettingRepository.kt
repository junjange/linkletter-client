package linkletter.client.core.data.repository

import kotlinx.coroutines.flow.Flow
import linkletter.client.core.data.source.AlarmSettingDataSource
import linkletter.client.core.domain.repository.AlarmSettingRepository

internal class DefaultAlarmSettingRepository(
    private val source: AlarmSettingDataSource,
) : AlarmSettingRepository {
    override val getAlarmEnabledFlow: Flow<Boolean> = source.getAlarmEnabledFlow

    override suspend fun setAlarmEnabled(enabled: Boolean) {
        source.setAlarmEnabled(enabled = enabled)
    }
}

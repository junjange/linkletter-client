package linkletter.client.core.domain.usecase

import linkletter.client.core.domain.repository.AlarmSettingRepository

internal class DefaultSetAlarmEnabledUseCase(
    private val repository: AlarmSettingRepository,
) : SetAlarmEnabledUseCase {
    override suspend fun invoke(enabled: Boolean) = repository.setAlarmEnabled(enabled = enabled)
}

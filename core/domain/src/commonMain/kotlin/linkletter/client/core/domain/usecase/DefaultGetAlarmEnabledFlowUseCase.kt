package linkletter.client.core.domain.usecase

import linkletter.client.core.domain.repository.AlarmSettingRepository

internal class DefaultGetAlarmEnabledFlowUseCase(
    private val repository: AlarmSettingRepository,
) : GetAlarmEnabledFlowUseCase {
    override fun invoke() = repository.getAlarmEnabledFlow
}

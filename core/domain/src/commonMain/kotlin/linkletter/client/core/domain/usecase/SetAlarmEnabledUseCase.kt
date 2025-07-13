package linkletter.client.core.domain.usecase

interface SetAlarmEnabledUseCase {
    suspend operator fun invoke(enabled: Boolean)
}

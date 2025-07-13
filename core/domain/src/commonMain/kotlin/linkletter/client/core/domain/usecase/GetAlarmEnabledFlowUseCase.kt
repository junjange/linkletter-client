package linkletter.client.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetAlarmEnabledFlowUseCase {
    operator fun invoke(): Flow<Boolean>
}

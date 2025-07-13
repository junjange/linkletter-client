package linkletter.client.core.datastore

import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AlarmSettingPreferences(
    provider: DataStoreProvider,
) {
    private val dataStore = provider.getDataStore()

    private val alarmKey = booleanPreferencesKey(KEY_ALARM_ENABLED)

    val alarmEnabledFlow: Flow<Boolean> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { prefs -> prefs[alarmKey] == true }

    suspend fun setAlarmEnabled(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[alarmKey] = enabled
        }
    }

    companion object {
        private const val KEY_ALARM_ENABLED = "alarm_enabled"
    }
}

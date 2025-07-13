package linkletter.client.core.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

expect class DataStoreProvider() {
    fun getDataStore(): DataStore<Preferences>
}

internal fun createDataStore(
    migrations: List<DataMigration<Preferences>> = emptyList(),
    producePath: () -> String,
) = PreferenceDataStoreFactory
    .createWithPath(
        corruptionHandler = null,
        migrations = migrations,
        produceFile = {
            producePath().toPath()
        },
    )

internal const val dataStoreFileName = "alarm.preferences_pb"

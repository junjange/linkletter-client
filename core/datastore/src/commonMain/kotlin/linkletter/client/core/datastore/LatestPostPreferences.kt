package linkletter.client.core.datastore

import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class LatestPostPreferences(
    provider: DataStoreProvider,
) {
    private val dataStore = provider.getDataStore()

    private fun keyFor(blogUrl: String) = stringPreferencesKey("latest_post_link_$blogUrl")

    fun latestPostLink(blogUrl: String): Flow<String?> =
        dataStore.data
            .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .map { prefs -> prefs[keyFor(blogUrl)] }

    suspend fun saveLatestPostLink(
        blogUrl: String,
        latestPostLink: String,
    ) {
        dataStore.edit { prefs ->
            prefs[keyFor(blogUrl)] = latestPostLink
        }
    }
}

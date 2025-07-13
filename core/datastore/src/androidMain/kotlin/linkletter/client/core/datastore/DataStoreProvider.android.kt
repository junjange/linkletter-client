package linkletter.client.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.java.KoinJavaComponent

actual class DataStoreProvider actual constructor() {
    val context: Context = KoinJavaComponent.getKoin().get()

    private val dataStore =
        createDataStore(
            emptyList(),
        ) {
            context.filesDir.resolve(dataStoreFileName).absolutePath
        }

    actual fun getDataStore(): DataStore<Preferences> = dataStore
}

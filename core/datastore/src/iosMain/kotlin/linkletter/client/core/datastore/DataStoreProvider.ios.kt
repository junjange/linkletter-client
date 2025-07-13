package linkletter.client.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSFileManager
import platform.Foundation.NSLibraryDirectory
import platform.Foundation.NSUserDomainMask

actual class DataStoreProvider actual constructor() {
    private val dataStore =
        createDataStore(
            emptyList(),
        ) {
            providePath()
        }

    @OptIn(ExperimentalForeignApi::class)
    private fun providePath(): String {
        val documentDirectory =
            NSFileManager.defaultManager.URLForDirectory(
                directory = NSLibraryDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
        return requireNotNull(documentDirectory).path + "/$dataStoreFileName"
    }

    actual fun getDataStore(): DataStore<Preferences> = dataStore
}

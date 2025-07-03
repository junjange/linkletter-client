package linkletter.client.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.java.KoinJavaComponent

actual fun getDatabaseBuilder(): RoomDatabase.Builder<LinkletterDatabase> {
    val appContext: Context = KoinJavaComponent.getKoin().get()
    val dbFile = appContext.getDatabasePath(DB_NAME)
    return Room.databaseBuilder<LinkletterDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}

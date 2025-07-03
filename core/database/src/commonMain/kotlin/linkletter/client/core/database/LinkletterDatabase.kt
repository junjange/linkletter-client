package linkletter.client.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import linkletter.client.core.database.dao.BlogInfoDao
import linkletter.client.core.database.entity.BlogInfoEntity

@Database(
    entities = [BlogInfoEntity::class],
    version = 1,
    exportSchema = false,
)
@ConstructedBy(LinkletterDatabaseDatabaseConstructor::class)
abstract class LinkletterDatabase : RoomDatabase() {
    abstract fun blogInfoDao(): BlogInfoDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object LinkletterDatabaseDatabaseConstructor : RoomDatabaseConstructor<LinkletterDatabase> {
    override fun initialize(): LinkletterDatabase
}

internal const val DB_NAME = "linkletter.db"

expect fun getDatabaseBuilder(): RoomDatabase.Builder<LinkletterDatabase>

fun getRoomDatabase(): LinkletterDatabase =
    getDatabaseBuilder()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

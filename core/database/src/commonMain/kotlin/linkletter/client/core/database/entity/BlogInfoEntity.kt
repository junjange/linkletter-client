package linkletter.client.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blogInfos")
data class BlogInfoEntity(
    @PrimaryKey
    val url: String,
    val name: String,
    val authorName: String,
    val authorImageUrl: String?,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
)

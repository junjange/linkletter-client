package linkletter.client.core.database.mapper

import kotlinx.datetime.Clock.System
import linkletter.client.core.database.entity.BlogInfoEntity
import linkletter.client.core.model.Author
import linkletter.client.core.model.BlogInfo

fun BlogInfo.toEntity(): BlogInfoEntity =
    BlogInfoEntity(
        url = this.url,
        name = this.name,
        authorName = this.author.name,
        authorImageUrl = this.author.imageUrl,
        updatedAt = System.now().epochSeconds,
    )

fun List<BlogInfoEntity>.toData(): List<BlogInfo> =
    this.map { blogInfoEntity ->
        blogInfoEntity.toData()
    }

fun BlogInfoEntity.toData(): BlogInfo =
    BlogInfo(
        url = this.url,
        name = this.name,
        author =
            Author(
                name = this.authorName,
                imageUrl = this.authorImageUrl,
            ),
    )

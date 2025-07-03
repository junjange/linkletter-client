package linkletter.client.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import linkletter.client.core.database.entity.BlogInfoEntity

@Dao
interface BlogInfoDao {
    @Query("SELECT * FROM blogInfos ORDER BY updatedAt DESC")
    fun getAllBlogInfos(): Flow<List<BlogInfoEntity>>

    @Query("SELECT * FROM blogInfos WHERE url = :blogUrl")
    suspend fun getBlogInfoByUrl(blogUrl: String): BlogInfoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlogInfo(blogInfo: BlogInfoEntity)

    @Update
    suspend fun updateBlogInfo(blogInfo: BlogInfoEntity)

    @Delete
    suspend fun deleteBlogInfo(blogInfo: BlogInfoEntity)

    @Query("DELETE FROM blogInfos WHERE url = :blogUrl")
    suspend fun deleteBlogInfoByUrl(blogUrl: String)

    @Query("SELECT EXISTS(SELECT 1 FROM blogInfos WHERE url = :blogUrl)")
    suspend fun isBlogInfoExists(blogUrl: String): Boolean
}

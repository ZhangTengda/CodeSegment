package com.xnjz.one.room2

import androidx.room.*

/**
 */
@Dao
interface ReadHistoryDao {
    @Transaction
    @Insert(entity = Article::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article): Long


    @Transaction
    @Query("SELECT * FROM article ORDER BY readTime DESC")
    suspend fun queryAllReadHistory(): List<ReadHistory>

    @Transaction
    @Query("SELECT * FROM article WHERE id = :id")
    suspend fun queryReadHistory(id: Long): ReadHistory?


    @Transaction
    @Delete(entity = Article::class)
    suspend fun deleteArticle(article: Article)


}
package com.tahadroid.thenews.data.local

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tahadroid.thenews.models.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = SQLiteDatabase.CONFLICT_REPLACE)
    suspend fun upsert(article: Article): Long
    @Delete
    suspend fun deleteArticle(article: Article)
    @Query("SELECT * FROM articles")
    fun getAllArticles():LiveData<List<Article>>
}
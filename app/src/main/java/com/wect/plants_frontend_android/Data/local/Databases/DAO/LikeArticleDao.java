package com.wect.plants_frontend_android.Data.local.Databases.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.wect.plants_frontend_android.Data.local.Databases.Entities.LikeArticle;

@Dao
public interface LikeArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(LikeArticle likeArticle);

    @Delete
    void delete(LikeArticle likeArticle);

    @Query("SELECT COUNT(*) > 0 FROM like_articles WHERE user_id = :userId AND article_id = :articleId")
    boolean isArticleLikedByUser(long userId, long articleId);

    @Query("SELECT COUNT(*) FROM like_articles WHERE article_id = :articleId")
    int getLikesCount(long articleId);
}


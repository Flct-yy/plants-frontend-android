package com.wect.plants_frontend_android.Data.local.Databases.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.wect.plants_frontend_android.Data.local.Databases.DTO.ArticleWithAuthorAndLike;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.Articles;

import java.util.List;

@Dao
public interface ArticlesDao {

    // 插入文章
    @Insert
    long insertArticle(Articles articles);

    // 更新文章
    @Update
    void updateArticle(Articles articles);

    // 删除文章
    @Delete
    void deleteArticle(Articles articles);

    // 获取所有文章（按时间倒序）
    @Query("SELECT * FROM articles ORDER BY created_at DESC")
    List<Articles> getAllArticles();

    // 根据用户获取文章
    @Query("SELECT * FROM articles WHERE author_id = :userId ORDER BY created_at DESC")
    List<Articles> getArticlesByUser(long userId);

    // 根据文章id获取
    @Query("SELECT * FROM articles WHERE id = :articleId LIMIT 1")
    Articles getArticleById(long articleId);

    // 增加浏览数
    @Query("UPDATE articles SET views = views + 1 WHERE id = :articleId")
    void increaseViews(long articleId);

    // 点赞 +1
    @Query("UPDATE articles SET likes = likes + 1 WHERE id = :articleId")
    void increaseLikes(long articleId);

    // 取消点赞 -1（避免小于0）
    @Query("UPDATE articles SET likes = CASE WHEN likes > 0 THEN likes - 1 ELSE 0 END WHERE id = :articleId")
    void decreaseLikes(long articleId);

    // 更新评论数
    @Query("UPDATE articles SET comments = comments + 1 WHERE id = :articleId")
    void increaseComments(long articleId);

    // 获取所有文章（实时监控变化）
    @Query("SELECT * FROM articles ORDER BY created_at DESC")
    LiveData<List<Articles>> getAllArticlesLive();

    // 根据用户获取文章（实时监控）
    @Query("SELECT * FROM articles WHERE author_id = :userId ORDER BY created_at DESC")
    LiveData<List<Articles>> getArticlesByUserLive(long userId);

    @Transaction
    @Query("SELECT * FROM articles WHERE id = :articleId")
    ArticleWithAuthorAndLike getArticleWithAuthor(long articleId);

    @Transaction
    @Query("SELECT * FROM articles ORDER BY created_at DESC")
    List<ArticleWithAuthorAndLike> getAllArticlesWithAuthors();

    // 获取用户点赞的文章列表（带作者信息）
    @Transaction
    @Query(
            "SELECT a.*, u.* " +
                    "FROM articles a " +
                    "INNER JOIN users u ON a.author_id = u.id " +
                    "INNER JOIN like_articles la ON a.id = la.article_id " +
                    "WHERE la.user_id = :userId " +
                    "ORDER BY la.created_at DESC")
    LiveData<List<ArticleWithAuthorAndLike>> getLikedArticlesByUser(long userId);

    // 获取文章详情（含作者和点赞状态）
    @Transaction
    @Query(
            "SELECT a.*, u.*, " +
                    "EXISTS (SELECT 1 FROM like_articles WHERE user_id = :userId AND article_id = a.id) AS isLiked " +
                    "FROM articles a " +
                    "INNER JOIN users u ON a.author_id = u.id " +
                    "WHERE a.id = :articleId")
    LiveData<ArticleWithAuthorAndLike> getArticleDetail(long articleId, long userId);
}


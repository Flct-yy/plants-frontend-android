package com.wect.plants_frontend_android.Data.local.Databases.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.wect.plants_frontend_android.Data.local.Databases.DTO.CommentWithUser;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.Comment;

import java.util.List;

@Dao
public interface CommentDao {
    @Insert
    void insert(Comment comment);

    @Query("SELECT * FROM comments WHERE article_id = :articleId ORDER BY created_at DESC")
    LiveData<List<Comment>> getCommentsByArticle(long articleId);

    @Query("SELECT COUNT(*) FROM comments WHERE article_id = :articleId")
    int getCommentCount(long articleId);

    @Transaction
    @Query("SELECT * FROM comments WHERE article_id = :articleId ORDER BY created_at DESC")
    LiveData<List<CommentWithUser>> getCommentsWithUser(long articleId);
}
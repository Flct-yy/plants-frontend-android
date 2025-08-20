package com.wect.plants_frontend_android.Data.local.Databases.Entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "like_articles",
        primaryKeys = {"user_id", "article_id"},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id", onDelete = CASCADE),
                @ForeignKey(entity = Articles.class, parentColumns = "id", childColumns = "article_id", onDelete = CASCADE)
        },
        indices = {@Index("user_id"), @Index("article_id")}
)
public class LikeArticle {

    @ColumnInfo(name = "user_id")
    private long userId;   // 哪个用户喜欢的

    @ColumnInfo(name = "article_id")
    private long articleId;  // 喜欢的文章Id

    @ColumnInfo(name = "created_at")
    private long createdAt = System.currentTimeMillis();  // 喜欢时间戳，方便排序

    // --- getter / setter ---
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getArticleId() {
        return articleId;
    }
    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}

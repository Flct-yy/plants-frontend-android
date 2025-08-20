package com.wect.plants_frontend_android.Data.local.Databases.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "articles",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",         // User 主键
                childColumns = "author_id",   // Articles 外键
                onDelete = ForeignKey.CASCADE // 删除用户时，删除该用户文章
        ),
        indices = {@Index("author_id"), @Index("created_at")}   // 建立索引，提高查询性能
)
public class Articles {

    @PrimaryKey(autoGenerate = true)
    private long id;

    // 标题
    @ColumnInfo(name = "title")
    private String title;

    // 内容
    @ColumnInfo(name = "content")
    private String content;

    // 作者Id (外键，指向User)
    @ColumnInfo(name = "author_id")
    private long authorId;

    // 发布时间
    @ColumnInfo(name = "created_at")
    private long createdAt;  // 存时间戳

    // 喜欢数
    @ColumnInfo(name = "likes")
    private int likes = 0;

    // 浏览数
    @ColumnInfo(name = "views")
    private int views = 0;

    // 评论数
    @ColumnInfo(name = "comments")
    private int comments = 0;

    // getter/setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

}

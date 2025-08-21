package com.wect.plants_frontend_android.Data.local.Databases.DTO;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import com.wect.plants_frontend_android.Data.local.Databases.Entities.Articles;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.User;

public class ArticleWithAuthorAndLike {
    @Embedded
    public Articles articles;

    @Relation(
            parentColumn = "author_id",
            entityColumn = "id"
    )
    public User author;

    @Ignore
    public boolean isLiked;

    // 添加无参构造函数（Room 需要）
    public ArticleWithAuthorAndLike() {
    }

    // 业务逻辑使用的构造函数
    public ArticleWithAuthorAndLike(Articles articles, User author, boolean isLiked) {
        this.articles = articles;
        this.author = author;
        this.isLiked = isLiked;
    }
}
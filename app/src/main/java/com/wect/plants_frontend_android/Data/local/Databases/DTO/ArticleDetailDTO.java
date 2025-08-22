package com.wect.plants_frontend_android.Data.local.Databases.DTO;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.wect.plants_frontend_android.Data.local.Databases.Entities.Articles;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.User;

import java.util.List;

public class ArticleDetailDTO {
    @Embedded
    public Articles article;

    @Relation(parentColumn = "author_id", entityColumn = "id")
    public User author;

    public boolean isLiked;
    public List<CommentWithUser> comments;
}
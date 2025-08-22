package com.wect.plants_frontend_android.Data.local.Databases.DTO;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.wect.plants_frontend_android.Data.local.Databases.Entities.Comment;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.User;

public class CommentWithUser {
    @Embedded
    public Comment comment;

    @Relation(parentColumn = "user_id", entityColumn = "id")
    public User user;
}
package com.wect.plants_frontend_android.Data.local.Databases.DTO;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.wect.plants_frontend_android.Data.local.Databases.Entities.Message;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.User;

public class MessageWithSender {
    @Embedded
    public Message message;

    @Relation(parentColumn = "sender_id", entityColumn = "id")
    public User sender;
}
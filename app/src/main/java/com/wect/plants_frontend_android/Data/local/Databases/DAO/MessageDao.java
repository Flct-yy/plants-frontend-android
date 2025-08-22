package com.wect.plants_frontend_android.Data.local.Databases.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.wect.plants_frontend_android.Data.local.Databases.DTO.MessageWithSender;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message message);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Message> messages);

    @Update
    void update(Message message);

    @Query("SELECT * FROM messages WHERE conversation_id = :conversationId ORDER BY created_at ASC")
    LiveData<List<Message>> getMessagesByConversation(String conversationId);

    @Query("SELECT * FROM messages WHERE id = :messageId")
    LiveData<Message> getMessageById(long messageId);

    @Query("SELECT * FROM messages WHERE (sender_id = :userId OR receiver_id = :userId) ORDER BY created_at DESC")
    LiveData<List<Message>> getUserMessages(long userId);

    @Query("UPDATE messages SET is_read = 1 WHERE conversation_id = :conversationId AND receiver_id = :userId AND is_read = 0")
    void markConversationAsRead(String conversationId, long userId);

    @Query("SELECT COUNT(*) FROM messages WHERE conversation_id = :conversationId AND is_read = 0 AND receiver_id = :userId")
    LiveData<Integer> getUnreadCount(String conversationId, long userId);

    @Query("DELETE FROM messages WHERE conversation_id = :conversationId")
    void deleteConversation(String conversationId);

    @Query("DELETE FROM messages")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM messages WHERE conversation_id = :conversationId ORDER BY created_at ASC")
    LiveData<List<MessageWithSender>> getMessagesWithSenders(String conversationId);
}
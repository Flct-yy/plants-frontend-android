package com.wect.plants_frontend_android.Data.local.Databases.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "messages",
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "sender_id", onDelete = CASCADE),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "receiver_id", onDelete = CASCADE)
        },
        indices = {
                @Index("sender_id"),
                @Index("receiver_id"),
                @Index("conversation_id"),
                @Index("created_at")
        }
)
public class Message {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "conversation_id")
    private String conversationId;

    @ColumnInfo(name = "sender_id")
    private long senderId;

    @ColumnInfo(name = "receiver_id")
    private long receiverId;

    private String content;

    @ColumnInfo(name = "message_type")
    private int messageType; // 1: text, 2: image, 3: voice, 4: video

    @ColumnInfo(name = "is_read")
    private boolean isRead;

    @ColumnInfo(name = "is_sent")
    private boolean isSent;

    @ColumnInfo(name = "is_delivered")
    private boolean isDelivered;

    @ColumnInfo(name = "created_at")
    private long createdAt = System.currentTimeMillis();

    public Message(long id, String conversationId, long senderId, long receiverId,
                         String content, int messageType, boolean isRead, boolean isSent,
                         boolean isDelivered, long createdAt) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.messageType = messageType;
        this.isRead = isRead;
        this.isSent = isSent;
        this.isDelivered = isDelivered;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public long getSenderId() { return senderId; }
    public void setSenderId(long senderId) { this.senderId = senderId; }

    public long getReceiverId() { return receiverId; }
    public void setReceiverId(long receiverId) { this.receiverId = receiverId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getMessageType() { return messageType; }
    public void setMessageType(int messageType) { this.messageType = messageType; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public boolean isSent() { return isSent; }
    public void setSent(boolean sent) { isSent = sent; }

    public boolean isDelivered() { return isDelivered; }
    public void setDelivered(boolean delivered) { isDelivered = delivered; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
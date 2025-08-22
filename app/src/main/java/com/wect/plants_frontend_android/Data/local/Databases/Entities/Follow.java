package com.wect.plants_frontend_android.Data.local.Databases.Entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "follows",
        primaryKeys = {"follower_id", "following_id"},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "follower_id", onDelete = CASCADE),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "following_id", onDelete = CASCADE)
        },
        indices = {@Index("follower_id"), @Index("following_id")}
)
public class Follow {
    @ColumnInfo(name = "follower_id")
    private long followerId;

    @ColumnInfo(name = "following_id")
    private long followingId;

    @ColumnInfo(name = "created_at")
    private long createdAt = System.currentTimeMillis();

    // getters and setters

    public long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(long followerId) {
        this.followerId = followerId;
    }

    public long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(long followingId) {
        this.followingId = followingId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
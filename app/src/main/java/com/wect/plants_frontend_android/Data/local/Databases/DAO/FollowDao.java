package com.wect.plants_frontend_android.Data.local.Databases.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.wect.plants_frontend_android.Data.local.Databases.Entities.Follow;

@Dao
public interface FollowDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Follow follow);

    @Delete
    void delete(Follow follow);

    @Query("SELECT COUNT(*) > 0 FROM follows WHERE follower_id = :followerId AND following_id = :followingId")
    boolean isFollowing(long followerId, long followingId);

    @Query("SELECT COUNT(*) FROM follows WHERE following_id = :userId")
    int getFollowerCount(long userId);

    @Query("SELECT COUNT(*) FROM follows WHERE follower_id = :userId")
    int getFollowingCount(long userId);
}

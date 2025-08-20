package com.wect.plants_frontend_android.Data.local.Databases.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wect.plants_frontend_android.Data.local.Databases.Entities.User;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.UserBasicInfo;

import java.util.List;

// 使用 @Dao 注解声明数据访问接口
@Dao
public interface UserDao {
    // 作用：插入新用户记录
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    // 作用：更新用户完整信息
    @Update
    void update(User user);

    // 作用：删除指定用户记录
    @Delete
    void delete(User user);


    // 作用：通过用户ID获取完整用户信息
    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(long userId);

    // 作用：获取所有用户的实时数据流
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    // 作用：删除所有用户记录
    @Query("DELETE FROM users")
    void deleteAllUsers();

    // 作用：原子性地增加用户的点赞数
    @Query("UPDATE users SET likes = likes + :increment WHERE id = :userId")
    void incrementLikes(long userId, int increment);

    // 作用：原子更新用户关注他人的数量
    @Query("UPDATE users SET following = following + :increment WHERE id = :userId")
    void incrementFollowing(long userId, int increment);

    // 作用：原子更新用户的粉丝数量
    @Query("UPDATE users SET followers = followers + :increment WHERE id = :userId")
    void incrementFollowers(long userId, int increment);

    // 作用：更新用户性别
    @Query("UPDATE users SET gender = :gender WHERE id = :userId")
    void updateGender(long userId, String gender);

    // 作用：更新用户头像URL
    @Query("UPDATE users SET avatar = :avatarUrl WHERE id = :userId")
    void updateAvatar(long userId, String avatarUrl);

    // 作用：快速获取用户头像地址
    @Query("SELECT avatar FROM users WHERE id = :userId")
    String getAvatarUrl(long userId);

    // 作用：通过手机号精确查找用户
    @Query("SELECT * FROM users WHERE phone = :phone LIMIT 1")
    User getUserByPhone(String phone);

    // 作用：更新用户的个人简介
    @Query("UPDATE users SET introduction = :introduction WHERE id = :userId")
    void updateIntroduction(long userId, String introduction);

    // 作用：获取轻量级用户信息（用于列表展示）
    @Query("SELECT id, name, avatar AS avatarUrl, likes FROM users")
    LiveData<List<UserBasicInfo>> getUserBasicInfo();

    // 作用：按点赞数降序分页查询用户
    // limit：每页数量
    // offset：偏移量（当前页×每页数量）
    @Query("SELECT * FROM users ORDER BY likes DESC LIMIT :limit OFFSET :offset")
    LiveData<List<User>> getPopularUsers(int limit, int offset);
}
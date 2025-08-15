package com.wect.plants_frontend_android.Data.local.Databases.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wect.plants_frontend_android.Data.local.Databases.Entities.User;

import java.util.List;

// 使用 @Dao 注解声明数据访问接口
@Dao
public interface UserDao {
    // 通过 @Insert, @Update, @Delete 定义基本操作
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    // 使用 @Query 编写自定义 SQL 查询
    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(long userId);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE FROM users")
    void deleteAllUsers();
}
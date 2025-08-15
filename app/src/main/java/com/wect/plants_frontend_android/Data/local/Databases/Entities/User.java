package com.wect.plants_frontend_android.Data.local.Databases.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// 使用 @Entity 注解标记为数据库表
@Entity(tableName = "users")
public class User {
    // 定义主键 (@PrimaryKey)
    @PrimaryKey(autoGenerate = true)
    private long id;

    // 通过 @ColumnInfo 定义列名和类型
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "created_at")
    private long createdAt = System.currentTimeMillis();

    // 构造函数
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters 和 Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
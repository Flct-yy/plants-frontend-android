package com.wect.plants_frontend_android.Data.local.Databases.Entities;

// 新增精简用户信息类
public class UserBasicInfo {
    public long id;
    public String name;
    public String avatar;
    public int likes;

    // 构造函数（Room 需要）
    public UserBasicInfo(long id, String name, String avatar, int likes) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.likes = likes;
    }
}

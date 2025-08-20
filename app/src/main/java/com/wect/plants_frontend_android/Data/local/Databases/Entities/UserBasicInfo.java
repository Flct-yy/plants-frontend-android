package com.wect.plants_frontend_android.Data.local.Databases.Entities;

// 新增精简用户信息类
public class UserBasicInfo {
    private long id;
    private String name;
    private String avatarUrl;
    private int likes;

    public UserBasicInfo(long id, String name, String avatarUrl, int likes) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.likes = likes;
    }

    // 添加 getter 方法
    public long getId() { return id; }
    public String getName() { return name; }
    public String getAvatarUrl() { return avatarUrl; }
    public int getLikes() { return likes; }
}

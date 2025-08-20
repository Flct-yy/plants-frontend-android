package com.wect.plants_frontend_android.Data.local.Databases.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    @ColumnInfo(name = "phone")
    private String phone;

    // 新增头像字段 - 存储头像的URL或本地路径
    @ColumnInfo(name = "avatar")
    private String avatarUrl = "";  // 默认空字符串，表示无头像

    // 简介
    @ColumnInfo(name = "introduction")
    private String introduction = "";  // 默认空字符串
    //性别
    @ColumnInfo(name = "gender")
    private String gender = "unknown"; // 默认值
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    public static final String GENDER_OTHER = "other";
    public static final String GENDER_UNKNOWN = "unknown";

    // 获赞
    @ColumnInfo(name = "likes")
    private int likes = 0;       // 改为数值类型
    // 关注
    @ColumnInfo(name = "following")
    private int following = 0;   // 改为数值类型
    // 粉丝
    @ColumnInfo(name = "followers")
    private int followers = 0;   // 改为数值类型

    // 保存的是 时间戳（timestamp）
    @ColumnInfo(name = "created_at")
    private long createdAt = System.currentTimeMillis();

    // 全字段构造函数（Room需要）
    public User(long id, String name, String phone,  String avatarUrl, String introduction,
                String gender, int likes, int following, int followers, long createdAt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.introduction = introduction;
        this.gender = gender;
        this.likes = likes;
        this.following = following;
        this.followers = followers;
        this.createdAt = createdAt;
    }

    // 简化构造函数（业务逻辑用）
    @Ignore
    public User(String name, String phone) {
        this(0, name, phone, "", "", "unknown", 0, 0, 0, System.currentTimeMillis());
    }

    // 带头像的简化构造函数
    @Ignore
    public User(String name, String phone, String avatarUrl) {
        this(0, name, phone, avatarUrl, "", "unknown", 0, 0, 0, System.currentTimeMillis());
    }


    // Getters
    public long getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getIntroduction() { return introduction; }
    public String getGender() { return gender; }
    public int getLikes() { return likes; }
    public int getFollowing() { return following; }
    public int getFollowers() { return followers; }
    public long getCreatedAt() { return createdAt; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl != null ? avatarUrl : "";
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction != null ? introduction : "";
    }

    public void setGender(String gender) {
        // 限制性别选项
        if (gender != null &&
                (gender.equals("male") || gender.equals("female") || gender.equals("other"))) {
            this.gender = gender;
        } else {
            this.gender = "unknown";
        }
    }

    public void setLikes(int likes) { this.likes = Math.max(likes, 0); }
    public void setFollowing(int following) { this.following = Math.max(following, 0); }
    public void setFollowers(int followers) { this.followers = Math.max(followers, 0); }
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt > 0 ? createdAt : System.currentTimeMillis();
    }

    // 实用方法 - 生成默认头像URL
    public String getDefaultAvatarUrl() {
        if (!avatarUrl.isEmpty()) return avatarUrl;

        // 根据名称生成默认头像
        return "https://ui-avatars.com/api/?name=" + name.replace(" ", "+") +
                "&background=random&size=200";
    }

    /**
     * // 登录专用DTO
     * public class LoginInfo {
     *     private String account;
     *     private String password;
     *     // 可能包含设备信息等登录相关字段
     * }
     *
     * // 基础用户信息
     * public class UserProfile {
     *     private Long userId;
     *     private String avatar;
     *     private String nickname;
     *     // 其他展示用字段
     * }
     *
     * // 完整用户信息（继承扩展）
     * public class UserDetail extends UserProfile {
     *     private String email;
     *     private String phone;
     *     private Date registerTime;
     *     // 其他敏感/详细字段
     * }
     */
}
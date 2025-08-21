package com.wect.plants_frontend_android.Data.local.Databases.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plants")
public class Plant {
    @PrimaryKey
    private long id;

    @ColumnInfo(name = "chinese_name")
    private String chineseName;

    @ColumnInfo(name = "latin_name")
    private String latinName;

    @ColumnInfo(name = "english_name")
    private String englishName;

    private String alias;
    private String family;
    private String genus;
    private String protectionLevel;
    private String characteristics;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "is_collected")
    private boolean isCollected;

    @ColumnInfo(name = "created_at")
    private long createdAt = System.currentTimeMillis();

    // 构造函数
    public Plant(long id, String chineseName, String latinName, String englishName,
                       String alias, String family, String genus, String protectionLevel,
                       String characteristics, String imageUrl, boolean isCollected, long createdAt) {
        this.id = id;
        this.chineseName = chineseName;
        this.latinName = latinName;
        this.englishName = englishName;
        this.alias = alias;
        this.family = family;
        this.genus = genus;
        this.protectionLevel = protectionLevel;
        this.characteristics = characteristics;
        this.imageUrl = imageUrl;
        this.isCollected = isCollected;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getChineseName() { return chineseName; }
    public void setChineseName(String chineseName) { this.chineseName = chineseName; }

    public String getLatinName() { return latinName; }
    public void setLatinName(String latinName) { this.latinName = latinName; }

    public String getEnglishName() { return englishName; }
    public void setEnglishName(String englishName) { this.englishName = englishName; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getFamily() { return family; }
    public void setFamily(String family) { this.family = family; }

    public String getGenus() { return genus; }
    public void setGenus(String genus) { this.genus = genus; }

    public String getProtectionLevel() { return protectionLevel; }
    public void setProtectionLevel(String protectionLevel) { this.protectionLevel = protectionLevel; }

    public String getCharacteristics() { return characteristics; }
    public void setCharacteristics(String characteristics) { this.characteristics = characteristics; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isCollected() { return isCollected; }
    public void setCollected(boolean collected) { isCollected = collected; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}

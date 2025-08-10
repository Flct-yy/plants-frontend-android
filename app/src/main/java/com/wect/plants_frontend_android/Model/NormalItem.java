package com.wect.plants_frontend_android.Model;

public class NormalItem {
    // 普通Item的数据
    public String name;
    public String stats;
    public int imageResId;

    public NormalItem(String name, String stats, int imageResId) {
        this.name = name;
        this.stats = stats;
        this.imageResId = imageResId;
    }
}
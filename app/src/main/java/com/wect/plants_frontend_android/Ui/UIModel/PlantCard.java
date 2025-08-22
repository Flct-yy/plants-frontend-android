package com.wect.plants_frontend_android.Ui.UIModel;

public class PlantCard {
    private int imageResId;       // 图片资源ID
    private String plantName;     // 中文名
    private String latinName;     // 拉丁学名
    private String englishName;   // 英文名
    private String alias;         // 别名
    private String classify;      // 科属种
    private String protection;    // 保护等级
    private String characteristic;// 特点
    private Boolean isCollect;// 是否收藏

    public PlantCard(int imageResId, String plantName, String latinName, String englishName,
                     String alias, String classify, String protection, String characteristic, Boolean isCollect) {
        this.imageResId = imageResId;
        this.plantName = plantName;
        this.latinName = latinName;
        this.englishName = englishName;
        this.alias = alias;
        this.classify = classify;
        this.protection = protection;
        this.characteristic = characteristic;
        this.isCollect = isCollect;
    }

    public int getImageResId() { return imageResId; }
    public String getPlantName() { return plantName; }
    public String getLatinName() { return latinName; }
    public String getEnglishName() { return englishName; }
    public String getAlias() { return alias; }
    public String getClassify() { return classify; }
    public String getProtection() { return protection; }
    public String getCharacteristic() { return characteristic; }
    public Boolean getCollect() {
        return isCollect;
    }

}


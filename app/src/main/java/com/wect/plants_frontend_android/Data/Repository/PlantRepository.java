package com.wect.plants_frontend_android.Data.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wect.plants_frontend_android.Ui.UIModel.PlantCard;
import com.wect.plants_frontend_android.R;

import java.util.ArrayList;
import java.util.List;

public class PlantRepository {

    public LiveData<List<PlantCard>> getPlants() {
        MutableLiveData<List<PlantCard>> liveData = new MutableLiveData<>();
        liveData.setValue(getMockPlants());
        return liveData;
    }

    private List<PlantCard> getMockPlants() {
        List<PlantCard> plantList = new ArrayList<>();

        // 原有的6个植物数据
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "兰花",
                "Orchidaceae",
                "Orchid",
                "草兰",
                "兰科 兰属",
                "一级保护",
                "花香清幽",
                true
        ));
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "玫瑰",
                "Rosa",
                "Rose",
                "月季",
                "蔷薇科 蔷薇属",
                "无保护等级",
                "花色多样",
                false
        ));
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "向日葵",
                "Helianthus annuus",
                "Sunflower",
                "葵花",
                "菊科 向日葵属",
                "无保护等级",
                "花盘大、向阳转动",
                true
        ));
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "银杏",
                "Ginkgo biloba",
                "Ginkgo",
                "白果树",
                "银杏科 银杏属",
                "二级保护",
                "叶形独特、寿命极长"
                ,true
        ));
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "仙人掌",
                "Cactaceae",
                "Cactus",
                "刺球",
                "仙人掌科 仙人掌属",
                "无保护等级",
                "耐旱、多刺",
                false
        ));
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "樱花",
                "Cerasus",
                "Cherry Blossom",
                "日本樱",
                "蔷薇科 樱属",
                "无保护等级",
                "花期短、观赏性强",
                false
        ));



        return plantList;
    }
}

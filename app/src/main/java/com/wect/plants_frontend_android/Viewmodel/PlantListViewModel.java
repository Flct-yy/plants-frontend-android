package com.wect.plants_frontend_android.Viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wect.plants_frontend_android.Ui.UIModel.PlantCard;
import com.wect.plants_frontend_android.Data.Repository.PlantRepository;

import java.util.List;

public class PlantListViewModel extends ViewModel {

    // 数据仓库，负责提供植物数据
    private final PlantRepository repository;
    // 可变的 LiveData 容器，持有植物列表数据
    private final MutableLiveData<List<PlantCard>> plantsLiveData = new MutableLiveData<>();

    public PlantListViewModel() {
        repository = new PlantRepository();
        loadPlants();
    }

    // 向 UI 层暴露 不可变的 LiveData 对象
    public LiveData<List<PlantCard>> getPlants() {
        return plantsLiveData;
    }

    private void loadPlants() {
        // 实际项目中这里可能是异步操作
        plantsLiveData.setValue(repository.getPlants().getValue());
    }

//    public void toggleCollect(int position) {
//        List<PlantCard> currentList = plantsLiveData.getValue();
//        if (currentList != null && position < currentList.size()) {
//            PlantCard plant = currentList.get(position);
//            plant.setCollect(!plant.isCollect());
//            plantsLiveData.postValue(currentList);
//        }
//    }
}

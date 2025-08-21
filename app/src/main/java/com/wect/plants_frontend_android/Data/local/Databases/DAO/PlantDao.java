package com.wect.plants_frontend_android.Data.local.Databases.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wect.plants_frontend_android.Data.local.Databases.Entities.Plant;

import java.util.List;

@Dao
public interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Plant plant);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Plant> plants);

    @Update
    void update(Plant plant);

    @Query("SELECT * FROM plants ORDER BY chinese_name ASC")
    LiveData<List<Plant>> getAllPlants();

    @Query("SELECT * FROM plants WHERE id = :plantId")
    LiveData<Plant> getPlantById(long plantId);

    @Query("SELECT * FROM plants WHERE chinese_name LIKE :query OR latin_name LIKE :query OR english_name LIKE :query")
    LiveData<List<Plant>> searchPlants(String query);

    @Query("SELECT * FROM plants WHERE is_collected = 1 ORDER BY chinese_name ASC")
    LiveData<List<Plant>> getCollectedPlants();

    @Query("UPDATE plants SET is_collected = :isCollected WHERE id = :plantId")
    void updateCollectionStatus(long plantId, boolean isCollected);

    @Query("DELETE FROM plants")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM plants")
    int getCount();
}
package com.tauqeer.madproject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface AccelerometerDAO {
    @Query("SELECT * FROM AccelerometerData")
    List<AccelerometerData> getAll();

    @Insert
    void insertAll(AccelerometerData accelerometerData);
}

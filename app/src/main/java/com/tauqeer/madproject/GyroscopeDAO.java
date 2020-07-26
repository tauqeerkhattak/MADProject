package com.tauqeer.madproject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface GyroscopeDAO {
    @Query("SELECT * FROM GyroscopeData")
    List<GyroscopeData> getAll();

    @Insert
    void insertAll(GyroscopeData gyroscopeData);
}

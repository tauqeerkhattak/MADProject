package com.tauqeer.madproject;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GyroscopeData.class},version = 1)
public abstract class GyroDatabase extends RoomDatabase {
    public abstract GyroscopeDAO gyroscopeDAO();
    public static GyroDatabase INSTANCE;
    public static GyroDatabase getGyroDatabase (Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),GyroDatabase.class,"GyroProject").build();
        }
        return INSTANCE;
    }
}

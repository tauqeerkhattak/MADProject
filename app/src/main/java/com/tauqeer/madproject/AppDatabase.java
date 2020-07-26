package com.tauqeer.madproject;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AccelerometerData.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AccelerometerDAO accelerometerDAO();
    private static AppDatabase INSTANCE;
    public static AppDatabase getAppDatabase (Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"ProjectDB").build();
        }
        return INSTANCE;
    }
}

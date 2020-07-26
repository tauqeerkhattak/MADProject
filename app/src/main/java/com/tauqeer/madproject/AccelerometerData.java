package com.tauqeer.madproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AccelerometerData {
    @PrimaryKey
    int entryNo;

    @ColumnInfo (name = "X")
    float x;

    @ColumnInfo (name = "Y")
    float y;

    @ColumnInfo (name = "Z")
    float z;

    public int getEntryNo () {
        return entryNo;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void setEntryNo(int entryNo) {
        this.entryNo = entryNo;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }
}

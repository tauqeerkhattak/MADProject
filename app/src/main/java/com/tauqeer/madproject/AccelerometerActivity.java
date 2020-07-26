package com.tauqeer.madproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    TextView xCoordinate,yCoordinate,zCoordinate;
    private SensorManager sensorManager;
    private float x,y,z;
    Sensor accelerometer;
    Button save,view;
    int entryNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        xCoordinate = (TextView) findViewById(R.id.xCoordinate);
        yCoordinate = (TextView) findViewById(R.id.yCoordinate);
        zCoordinate = (TextView) findViewById(R.id.zCoordinate);
        save = (Button) findViewById(R.id.saveToDatabase);
        view = (Button) findViewById(R.id.viewDatabase);
        SharedPreferences settings = getSharedPreferences("EntryNumberSaving",Context.MODE_PRIVATE);
        entryNo = settings.getInt("EntryNumber",0);

        save.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    @Override
    public void onPause () {
        super.onPause();
        sensorManager.unregisterListener(this);
        SharedPreferences settings;
        settings = getSharedPreferences("EntryNumberSaving",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("EntryNumber",entryNo);
        editor.commit();
    }

    @Override
    public void onResume () {
        super.onResume();
        sensorManager.registerListener(this,accelerometer,sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];
        xCoordinate.setText( "X:  " + x +" m/s2");
        yCoordinate.setText("Y:  " + y + " m/s2");
        zCoordinate.setText("Z:  " + z + " m/s2");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onClick(View view) {
        int eventSource = view.getId();
        if (eventSource == R.id.saveToDatabase) {
            saveData();
        }
        else if (eventSource == R.id.viewDatabase) {
            Intent intent = new Intent(AccelerometerActivity.this,ViewAccelerometerDataActivity.class);
            startActivity(intent);
        }
    }

    private void saveData () {
        class SaveData extends AsyncTask<Void,Void,Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                AccelerometerData data = new AccelerometerData();
                data.setEntryNo(entryNo++);
                data.setX(x);
                data.setY(y);
                data.setZ(z);
                AppDatabase.getAppDatabase(getApplicationContext()).accelerometerDAO().insertAll(data);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Sensor Info saved in Database", Toast.LENGTH_SHORT).show();
            }
        }
        SaveData saveData = new SaveData();
        saveData.execute();
    }
}
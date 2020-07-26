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

public class GyroscopeActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    TextView x,y,z;
    Button save,view;
    private SensorManager sensorManager;
    Sensor gyroscope;
    private float xFloat,yFloat,zFloat;
    int entryGyroNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        x = (TextView) findViewById(R.id.xGyroscope);
        y = (TextView) findViewById(R.id.yGyroscope);
        z = (TextView) findViewById(R.id.zGyroscope);
        save = (Button) findViewById(R.id.saveGyroData);
        view = (Button) findViewById(R.id.viewGyroData);
        SharedPreferences settings = getSharedPreferences("GyroEntrySaved",Context.MODE_PRIVATE);
        entryGyroNo = settings.getInt("GyroEntryNo",0);

        save.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,gyroscope,sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        SharedPreferences settings;
        settings = getSharedPreferences("GyroEntrySaved",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("GyroEntryNo", entryGyroNo);
        editor.commit();
    }

    @Override
    public void onClick(View view) {
        int eventSource = view.getId();
        if (eventSource == R.id.saveGyroData) {
            saveData();
        }
        else if (eventSource == R.id.viewGyroData) {
            Intent intent = new Intent(GyroscopeActivity.this,ViewGyroscopeDataActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        xFloat = sensorEvent.values[0];
        yFloat = sensorEvent.values[1];
        zFloat = sensorEvent.values[2];
        x.setText("X:      " + xFloat + " rad/s");
        y.setText("Y:      " + yFloat + " rad/s");
        z.setText("Z:      " + zFloat + " rad/s");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void saveData () {
        class SaveData extends AsyncTask<Void,Void,Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                GyroscopeData data = new GyroscopeData();
                data.setEntryNo(entryGyroNo++);
                data.setX(xFloat);
                data.setY(yFloat);
                data.setZ(zFloat);
                GyroDatabase.getGyroDatabase(getApplicationContext()).gyroscopeDAO().insertAll(data);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Gyroscope Data saved in Database", Toast.LENGTH_SHORT).show();
            }
        }
        SaveData saveData = new SaveData();
        saveData.execute();
    }
}
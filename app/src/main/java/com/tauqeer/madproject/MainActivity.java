package com.tauqeer.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,PopupMenu.OnMenuItemClickListener{
    Button selectSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectSensor = (Button) findViewById(R.id.selectSensor);
        selectSensor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.selectSensor) {
            showPopup(view);
        }
    }

    public void showPopup(View view) {
        PopupMenu selectSensorPopup = new PopupMenu(MainActivity.this,view);
        MenuInflater inflater = selectSensorPopup.getMenuInflater();
        inflater.inflate(R.menu.pop_menu,selectSensorPopup.getMenu());
        selectSensorPopup.show();
        selectSensorPopup.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.accelerometer_btn:
                Intent accelerometerIntent = new Intent(MainActivity.this,AccelerometerActivity.class);
                startActivity(accelerometerIntent);
                return true;
            case R.id.gyroscope_btn:
                Intent gyroscopeIntent = new Intent(MainActivity.this,GyroscopeActivity.class);
                startActivity(gyroscopeIntent);
                return true;
            default:
                return false;
        }
    }
}
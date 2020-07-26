package com.tauqeer.madproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class ViewAccelerometerDataActivity extends AppCompatActivity {

    ListView listView;
    List<AccelerometerData> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accelerometer_data);
        listView = (ListView) findViewById(R.id.listView);
        getData();
    }

    public void getData() {
        class GetData extends AsyncTask<Void,Void,List<AccelerometerData>> {
            @Override
            protected List<AccelerometerData> doInBackground(Void... voids) {
                dataList = AppDatabase.getAppDatabase(getApplicationContext()).accelerometerDAO().getAll();
                return dataList;
            }

            @Override
            protected void onPostExecute(List<AccelerometerData> accelerometerData) {
                super.onPostExecute(accelerometerData);
                ArrayList <String> finalList = new ArrayList();
                for (AccelerometerData data : accelerometerData) {
                    finalList.add(data.entryNo+"      X: "+data.getX()+"      Y: "+data.getY()+"      Z: "+data.getZ());
                }
                ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(ViewAccelerometerDataActivity.this,android.R.layout.simple_list_item_1,finalList);
                listView.setAdapter(arrayAdapter);
            }
        }
        GetData getData = new GetData();
        getData.execute();
    }
}
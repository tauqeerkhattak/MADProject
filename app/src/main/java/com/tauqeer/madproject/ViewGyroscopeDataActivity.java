package com.tauqeer.madproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class ViewGyroscopeDataActivity extends AppCompatActivity {

    ListView  listView;
    List<GyroscopeData> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gyroscope_data);
        listView = (ListView) findViewById(R.id.listView1);
        getData();
    }

    public void getData () {
        class GetData extends AsyncTask<Void,Void,List<GyroscopeData>> {
            @Override
            protected List<GyroscopeData> doInBackground(Void... voids) {
                dataList = GyroDatabase.getGyroDatabase(getApplicationContext()).gyroscopeDAO().getAll();
                return dataList;
            }

            @Override
            protected void onPostExecute(List<GyroscopeData> gyroscopeData) {
                super.onPostExecute(gyroscopeData);
                ArrayList <String> finalList = new ArrayList();
                for (GyroscopeData data : gyroscopeData) {
                    finalList.add(data.entryNo + "      X: "+ data.getX() +"      Y: " + data.getY() + "       Z: " + data.getZ());
                }
                ArrayAdapter <String> adapter = new ArrayAdapter(ViewGyroscopeDataActivity.this,android.R.layout.simple_list_item_1,finalList);
                listView.setAdapter(adapter);
            }
        }
        GetData getData = new GetData();
        getData.execute();
    }
}
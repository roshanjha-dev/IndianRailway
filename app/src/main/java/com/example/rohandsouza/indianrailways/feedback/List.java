package com.example.rohandsouza.indianrailways.feedback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rohandsouza.indianrailways.R;

public class List extends AppCompatActivity {

    String stations[] = TrainNo.stations;
    static String passed_station;
    ListView l;
    static int p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        l = (ListView)findViewById(R.id.list);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(List.this, R.layout.support_simple_spinner_dropdown_item, stations);
        l.setAdapter(arr);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                passed_station = (String)adapterView.getItemAtPosition(i);
                p1 = i;
                startActivity(new Intent(List.this, TrainNo.class));
            }
        });
    }
}

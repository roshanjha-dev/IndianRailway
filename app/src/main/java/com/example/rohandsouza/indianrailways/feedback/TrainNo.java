package com.example.rohandsouza.indianrailways.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rohandsouza.indianrailways.R;

public class TrainNo extends AppCompatActivity {
    static String stations[];
    int distance[];
    String passed_station = List.passed_station;
    static String Train_no;
    static String coming_station;
    int dist;
    EditText e1;
    TextView t;
    Button b1, b2, b3;
    Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_no);

        e1 = (EditText)findViewById(R.id.editText15);
        t = (TextView)findViewById(R.id.textView15);
        b1 = (Button)findViewById(R.id.button17);
        b2 = (Button)findViewById(R.id.button18);
        b3 = (Button)findViewById(R.id.button20);
       // s = (Spinner)findViewById(R.id.spinner);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().equals("12561")){
                    e1.setText("12561");
                    Train_no = e1.getText().toString();
                    stations = new String[]{"Samastipur", "Kanpur Central", "Aligarh", "Ghaziabad", "New Delhi"};
                    distance = new int[]{0, 13, 20, 25, 31, 38, 45, 52, 58, 63, 70, 76};
                   // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TrainNo.this,R.layout.support_simple_spinner_dropdown_item, stations);
                   // s.setAdapter(arrayAdapter);

                    startActivity(new Intent(TrainNo.this, List.class));
                    if(passed_station.toString().equals("Samastipur")){
                        t.setText("Kanpur Central");
                    }
                    else if(passed_station.toString().equals("Kanpur Central")){
                        t.setText("Aligarh");
                    }
                    else{
                        t.setText(null);
                    }
                }
                else if(e1.getText().toString().equals("15910")){
                    Train_no = e1.getText().toString();
                    stations = new String[]{"Lalgarh", "Bathinda", "Bareta", "Narwana", "Jind", "Bahadurgarh", "Delhi Kisan Ganj", "Ghaziabad", "Moradabad", "Bareilly", "Lucknow Charbagh", "Gorakhpur", "Sonpur"};
                    distance = new int[]{0, 13, 20, 25, 31, 38, 45, 52, 58, 63, 70, 76, 84};
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TrainNo.this,R.layout.support_simple_spinner_dropdown_item, stations);
                    s.setAdapter(arrayAdapter);

                }
            }
        });

       /* b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                s.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        passed_station = (String)adapterView.getItemAtPosition(i);
                        dist = distance[i+1] - distance[i];
                        if(dist >= 5){
                            coming_station = (String)adapterView.getItemAtPosition(i+1);
                        }
                        else{
                            coming_station = (String)adapterView.getItemAtPosition(i+2);
                        }
                    }
                });
            }
        });*/

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrainNo.this,Departments.FeedbackData.class));
            }
        });
    }
}

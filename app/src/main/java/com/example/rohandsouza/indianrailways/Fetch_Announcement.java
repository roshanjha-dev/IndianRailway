package com.example.rohandsouza.indianrailways;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Fetch_Announcement extends AppCompatActivity {

    String Json_list;
    String Json_String;
    String get_url;
    EditText e;
    Button b;
    JSONArray jsonArray;
    CustomAdapter customAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch__announcement);


        e = (EditText)findViewById(R.id.editText6);
        b = (Button)findViewById(R.id.button7);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String station = e.getText().toString();
                if(station.equals("Jaipur") || station.equals("jaipur")){
                    get_url = "http://192.168.43.120/webapp/jaipur_announcement.php";
                    new Background().execute();
                }
                else if(station.equals("Delhi") || station.equals("delhi")){
                    get_url = "http://192.168.43.120/webapp/delhi_announcement.php";
                    new Background().execute();
                }
                else {
                    Toast.makeText(Fetch_Announcement.this,"This station does not exist. Search again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    class Background extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(get_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((Json_String = bufferedReader.readLine()) != null) {
                    stringBuilder.append(Json_String + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Json_list = result;
            customAdapter = new CustomAdapter(Fetch_Announcement.this, R.layout.announcementlayout);
            listView = (ListView)findViewById(R.id.listView);
            listView.setAdapter(customAdapter);
            try {
                JSONObject JO;
                jsonArray = new JSONArray(Json_list);
                String date, train_no, announcement;

                int count = 0;

                while (count<jsonArray.length()){
                    JO = jsonArray.getJSONObject(count);
                    date = JO.getString("date");
                    train_no = JO.getString("train_no");
                    announcement = JO.getString("announcement");
                    DisplayAnnouncement displayAnnouncement = new DisplayAnnouncement(date, train_no,announcement);
                    customAdapter.add(displayAnnouncement);
                    count++;
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        }
    }
}

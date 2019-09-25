package com.example.rohandsouza.indianrailways;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rohandsouza.indianrailways.feedback.Departments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {

    String username;
    String userpass;
    static int l = 1;

    CircleImageView b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        b1 = (CircleImageView) findViewById(R.id.button4);
        b2 = (CircleImageView) findViewById(R.id.button6);
        b3 = (CircleImageView) findViewById(R.id.button5);

        username = Login.user_name;
        userpass = Login.user_pass;


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Fetch_Announcement.class));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LockerInfo lockerInfo = new LockerInfo(Home.this);
                lockerInfo.execute(username, userpass);

                if(l == 1) {
                    l = 2;
                    startActivity(new Intent(Home.this, Locker.class));
                }
                else if(l == 2){
                    l = 1;
                    startActivity(new Intent(Home.this, OpenLocker.class));
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Departments.class));
            }
        });
    }

    public class LockerInfo extends AsyncTask<String,Void,String>{
        Context ctx;
        LockerInfo(Context ctx){
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String locker_url = "http:// 192.168.43.120/webapp/locker_info.php";
            String user_name = params[0];
            String user_pass = params[1];
            try {
                URL url = new URL(locker_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") +"&"
                        + URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result ="";
                String line = "";

                while((line = bufferedReader.readLine()) != null)
                {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

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
        public void onPostExecute(String result) {
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }
    }
}


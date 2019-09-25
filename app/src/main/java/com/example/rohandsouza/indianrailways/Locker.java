package com.example.rohandsouza.indianrailways;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import net.glxn.qrgen.android.QRCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Locker extends AppCompatActivity {

    String user_name = Login.user_name;
    String locker_number;
    String Json_list;
    String Json_String;
    String get_url;
    EditText e;
    Button b;
    JSONArray jsonArray;
    LockerCustomAdapter lockerCustomAdapter;
    ListView listView;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker);

        b1 = (Button)findViewById(R.id.button16);
        e = (EditText)findViewById(R.id.station);
        b = (Button)findViewById(R.id.search);

        listView = (ListView)findViewById(R.id.listView2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String station = e.getText().toString();
                if(station.equals("Jaipur") || station.equals("jaipur")){
                    get_url = "http://192.168.43.120/webapp/jaipur_locker.php";
                    new Background().execute();
                }
                else if(station.equals("Delhi") || station.equals("delhi")){
                    get_url = "http://192.168.43.120/webapp/delhi_locker.php";
                    new Background().execute();
                }
                else {
                    Toast.makeText(Locker.this,"This station does not exist. Search again", Toast.LENGTH_LONG).show();
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String station = e.getText().toString();
                if(station.length() == 0){
                    Toast.makeText(Locker.this, "Please enter the station", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(Locker.this, Locker_confirm.class);
                    i.putExtra("locker_no", locker_number);
                    startActivity(i);
                    finish();
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
            lockerCustomAdapter = new LockerCustomAdapter(Locker.this, R.layout.lockerlayout);

            listView.setAdapter(lockerCustomAdapter);
            try {
                JSONObject JO;
                jsonArray = new JSONArray(Json_list);
                String locker_no, amount;

                int count = 0;

                while (count<jsonArray.length()){
                    JO = jsonArray.getJSONObject(count);
                    locker_no = JO.getString("locker_no");
                    amount = JO.getString("amount");
                    LockerDisplay lockerDisplay = new LockerDisplay(locker_no, amount);
                    lockerCustomAdapter.add(lockerDisplay);
                    count++;
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        }
    }

    public class LockerDisplay{
        private String locker_no, amount;

        public LockerDisplay(String locker_no, String amount) {
            this.setLocker_no(locker_no);
            this.setAmount(amount);
        }

        public String getLocker_no() {
            return locker_no;
        }

        public void setLocker_no(String locker_no) {
            this.locker_no = locker_no;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    public class LockerCustomAdapter extends ArrayAdapter {
        List list = new ArrayList();
        public LockerCustomAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }


        public void add(LockerDisplay object) {
            super.add(object);
            list.add(object);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View row;
            row = convertView;
            Display display2;
            if(row == null)
            {
                LayoutInflater layoutInflater = (LayoutInflater) LockerCustomAdapter.this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.lockerlayout,parent,false);
                display2 = new Display();
                display2.locker_no = (TextView) row.findViewById(R.id.textView2);
                display2.amount = (TextView)row.findViewById(R.id.textView6);
                row.setTag(display2);

            }
            else{
                display2 = (Display)row.getTag();
            }

            final LockerDisplay lockerDisplay = (LockerDisplay) LockerCustomAdapter.this.getItem(position);
            display2.locker_no.setText(lockerDisplay.getLocker_no());
            display2.amount.setText(lockerDisplay.getAmount());

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(true);
                    locker_number = lockerDisplay.getLocker_no().toString();
                }
            });

            return row;
        }

        public class Display
        {
            TextView locker_no, amount;

        }
    }
}

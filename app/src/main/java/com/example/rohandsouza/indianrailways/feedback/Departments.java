package com.example.rohandsouza.indianrailways.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohandsouza.indianrailways.Home;
import com.example.rohandsouza.indianrailways.R;

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

public class Departments extends AppCompatActivity implements FeedbackDialog.FeedbackDialogListener {

    CircleImageView b1, b2, b3, b4, b5;
    TextView t1, t2, t3, t4, t5;
    static String department;
    String pnr_no, coach_no, problems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);

        t1 = (TextView)findViewById(R.id.electrical);
        t2 = (TextView)findViewById(R.id.cleaning);
        t3 = (TextView)findViewById(R.id.coachwater);
        t4 = (TextView)findViewById(R.id.food);
        t5 = (TextView)findViewById(R.id.medicare);

        b1 = (CircleImageView) findViewById(R.id.button8);
        b2 = (CircleImageView) findViewById(R.id.button9);
        b3 = (CircleImageView) findViewById(R.id.button10);
        b4 = (CircleImageView) findViewById(R.id.button11);
        b5 = (CircleImageView) findViewById(R.id.button12);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                department = t1.getText().toString();
                //startActivity(new Intent(Departments.this, Feedback.class));
                openDialog();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                department = t2.getText().toString();
                //startActivity(new Intent(Departments.this, Feedback.class));
                openDialog();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                department = t3.getText().toString();
                //startActivity(new Intent(Departments.this, Feedback.class));
                openDialog();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                department = t4.getText().toString();
                //startActivity(new Intent(Departments.this, Feedback.class));
                openDialog();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                department = t5.getText().toString();
                //startActivity(new Intent(Departments.this, Feedback.class));
                openDialog();
            }
        });
    }

    public void openDialog(){
        FeedbackDialog feedbackDialog = new FeedbackDialog();
        feedbackDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String pnrno, String coachno, String problem) {
        pnr_no = pnrno;
        coach_no = coachno;
        problems = problem;
        Toast.makeText(this, pnr_no+coach_no+problems, Toast.LENGTH_SHORT).show();

        String type = "writedata";
        FeedbackData feedbackData = new FeedbackData(Departments.this);
        feedbackData.execute(type, pnr_no, department, coach_no, problem);
        startActivity(new Intent(Departments.this, Home.class));

        finish();
    }

    public class FeedbackData extends AsyncTask<String, Void, String> {

        Context ctx;
        FeedbackData(Context ctx)
        {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String feedback_url = "";
            String type = params[0];

            if(type.equals("writedata")){

                String pnr_no = params[1];
                String department = params[2];
                String coach_no = params[3];
                String problem = params[4];

                feedback_url = "http://192.168.43.120/webapp/jaipur_feedback.php";
              /*  if(station.equals("Jaipur") || station.equals("jaipur")){
                    feedback_url = "http://192.168.43.120/webapp/jaipur_feedback.php";
                }
                else if(station.equals("Delhi") || station.equals("delhi")){
                    feedback_url = "http://192.168.43.120/webapp/jaipur_feedback.php";
                } */

                try {
                    URL url = new URL(feedback_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("department", "UTF-8") + "=" + URLEncoder.encode(department, "UTF-8") + "&"
                            + URLEncoder.encode("pnr_no", "UTF-8") + "=" + URLEncoder.encode(pnr_no, "UTF-8") + "&"
                            + URLEncoder.encode("coach_no", "UTF-8") + "=" + URLEncoder.encode(coach_no, "UTF-8") + "&"
                            + URLEncoder.encode("problem", "UTF-8") + "=" + URLEncoder.encode(problem, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null){
                        result += line;
                    }
                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}

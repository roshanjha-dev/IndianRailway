package com.example.rohandsouza.indianrailways;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import static com.example.rohandsouza.indianrailways.Login.user_name;
import static com.example.rohandsouza.indianrailways.Login.user_pass;

/**
 * Created by Rohan Dsouza on 17-Mar-18.
 */

public class Locker_confirm extends AppCompatActivity {


    String username;
    String userpass;
    String number;
    String phone_number;
    TextView t1, t2;
    EditText e1, e2;
    Button b1, b2, b3;
    String locker_no;
    static int l;
    String verification_code;

    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locker_selection);

        auth = FirebaseAuth.getInstance();

        username = Login.user_name;
        userpass = Login.user_pass;

        l = Home.l;

        t1 = (TextView)findViewById(R.id.textView12);
        e1 = (EditText)findViewById(R.id.editText7);
        e2 = (EditText)findViewById(R.id.editText12);
        b1 = (Button)findViewById(R.id.button13);
        b2 = (Button)findViewById(R.id.button14);

        Intent i = getIntent();
        locker_no = i.getStringExtra("locker_no");

        t1.setText(locker_no);
        numberfetch();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = e1.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS, Locker_confirm.this, mCallback);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_code = e2.getText().toString();
                verifyPhoneNumber(verification_code, input_code);

                AlertDialog.Builder builder = new AlertDialog.Builder(Locker_confirm.this);
                builder.setTitle("Message")
                        .setMessage("The lock is opened. Keep your belongings and press Done or press cancel to leave.")
                        .setIcon(R.drawable.openlockicon)
                        .setCancelable(false);
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Locker_confirm.this, "The locker is locked!!!", Toast.LENGTH_SHORT).show();
                        l = 2;
                        startActivity(new Intent(Locker_confirm.this, Home.class));
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Locker_confirm.this, "Thank You! To open the locker again please verify the code.", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        });

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verification_code = s;
                Toast.makeText(getApplicationContext(), "Code sent to the number...", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void signInWithPhone(PhoneAuthCredential credential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "The locker is opened succesfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void verifyPhoneNumber(String verifyCode, String input_code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode, input_code);
        signInWithPhone(credential);
    }

    public void numberfetch(){
        FetchNumberClass fetchNumberClass = new FetchNumberClass(Locker_confirm.this);
        fetchNumberClass.execute(username, userpass);
        e1.setText(phone_number);
    }

    public class FetchNumberClass extends AsyncTask<String,Void,String>{
        Context ctx;

        public FetchNumberClass(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String number_url = "http://192.168.43.120/webapp/fetch_number.php";
            String un = params[0];
            String pw = params[1];
            try {
                URL url = new URL(number_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(un, "UTF-8") +"&"
                        + URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(pw, "UTF-8");

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
        protected void onPostExecute(String s) {
            Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.rohandsouza.indianrailways;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    static String user_name;
    static String user_pass;
    EditText e1, e2;
    Button b1, b2, b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b1 = (Button)findViewById(R.id.button);
        b2 = (Button)findViewById(R.id.button2);
        b3 = (Button)findViewById(R.id.button4);

        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText)findViewById(R.id.editText2);

        ConnectivityManager cm = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo Info = cm.getActiveNetworkInfo();
        if(Info != null && Info.isConnected()){

        }
        else {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(Login.this);
            a_builder.setMessage("Please enable internet connection !!!")
                    .setCancelable(false)
                    .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                            startActivity(in);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("No Internet Connection");
            alert.show();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((e1.length()== 0) && (e2.length()== 0)) {
                    Toast.makeText(Login.this, "Enter the username and password", Toast.LENGTH_LONG).show();
                }
                else{
                    user_name = e1.getText().toString();
                    user_pass = e2.getText().toString();
                    String type = "Login";
                    BackgroundTask backgroundTask = new BackgroundTask(Login.this);
                    backgroundTask.execute(type, user_name, user_pass);
                    startActivity(new Intent(Login.this, Home.class));
                    finish();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });


    }
}

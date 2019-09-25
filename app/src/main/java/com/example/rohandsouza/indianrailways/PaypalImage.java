package com.example.rohandsouza.indianrailways;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class PaypalImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_image);

        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(PaypalImage.this, Home.class));
                Toast.makeText(PaypalImage.this, "The Locker is opened", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, 5000);
    }
}

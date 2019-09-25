package com.example.rohandsouza.indianrailways;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paypal.android.sdk.payments.PayPalConfiguration;

public class OpenLocker extends AppCompatActivity {

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("YOUR CLIENT ID");

    EditText e1;
    Button b1, b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_locker);

        e1 = (EditText)findViewById(R.id.editText13);
        b1 = (Button)findViewById(R.id.openLock);
        b2 = (Button)findViewById(R.id.button15);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setText("894321");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OpenLocker.this, PaypalImage.class));
                finish();
            }
        });
    }
}

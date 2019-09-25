package com.example.rohandsouza.indianrailways;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

    EditText name, username, userpass, phoneno;
    String sname, susername, suserpass, sphoneno;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.editText3);
        username = (EditText)findViewById(R.id.editText4);
        userpass = (EditText)findViewById(R.id.editText5);
        phoneno = (EditText)findViewById(R.id.editText8);
        b = (Button)findViewById(R.id.button3);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((name.length()==0) && (username.length() == 0) && (userpass.length() == 0) && (phoneno.length()==0)){
                    Toast.makeText(Register.this, "Enter all the details", Toast.LENGTH_LONG).show();
                }
                else {
                    sname = name.getText().toString();
                    susername = username.getText().toString();
                    suserpass = userpass.getText().toString();
                    sphoneno = phoneno.getText().toString();
                    String type = "register";
                    BackgroundTask backgroundTask = new BackgroundTask(Register.this);
                    backgroundTask.execute(type, sname, susername, suserpass, sphoneno);
                    startActivity(new Intent(Register.this, Home.class));
                    finish();
                }
            }
        });

    }
}

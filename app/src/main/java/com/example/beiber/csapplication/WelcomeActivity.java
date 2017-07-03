package com.example.beiber.csapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button admin,client;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_admin);

        client = (Button) findViewById(R.id.client);
        admin = (Button) findViewById(R.id.admin);

        client.setOnClickListener(this);
        admin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v==client){
            startActivity(new Intent(this,LoginActivity.class));
        }

        if(v==admin){
            startActivity(new Intent(this,AdminLoginActivity.class));

        }

    }
}

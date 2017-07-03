package com.example.beiber.csapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by BEIBER on 6/20/2017.
 */

public class AdminLoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextUsername,editTextPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login_layout);

        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(this);
    }

    public void adminLogin(){

        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if((username.equals("hemal"))&&(password.equals("118"))){
            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,ClientListActivity.class));
        }else{
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {

        if(v==buttonLogin){
            //show all users
            adminLogin();
        }

    }
}

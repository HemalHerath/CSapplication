package com.example.beiber.csapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textviewUserEmail;

    private DatabaseReference databaseReference;

    private EditText editTextFirstName,editTextAddress,editTextTel,editTextAccNo,editTextCardNo,editTextBranch;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("clients");

        editTextFirstName = (EditText)findViewById(R.id.editTextFirstName);
        editTextAddress = (EditText)findViewById(R.id.editTextAddress);
        editTextTel = (EditText)findViewById(R.id.editTextTel);
        editTextAccNo = (EditText) findViewById(R.id.editTextAccNo);
        editTextCardNo = (EditText) findViewById(R.id.editCardNo);
        editTextBranch = (EditText) findViewById(R.id.editTextBranch);

        buttonSave = (Button)findViewById(R.id.buttonSave);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textviewUserEmail = (TextView)findViewById(R.id.textviewUserEmail);

        textviewUserEmail.setText(user.getEmail());

        buttonSave.setOnClickListener(this);
    }

    private void saveUserInformation(){

        String firstName = editTextFirstName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String tel = editTextTel.getText().toString().trim();
        String accNo = editTextAccNo.getText().toString().trim();
        String cardNo = editTextCardNo.getText().toString().trim();
        String branch = editTextBranch.getText().toString().trim();

        if(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(address) || TextUtils.isEmpty(tel) ||
                TextUtils.isEmpty(accNo) || TextUtils.isEmpty(branch) || TextUtils.isEmpty(cardNo)){

            Toast.makeText(this, "All fields must be filled",Toast.LENGTH_LONG).show();
        }
        else {

            FirebaseUser user = firebaseAuth.getCurrentUser();
            userInformation userInformation = new userInformation(firstName, address, tel, accNo, cardNo, branch);
            databaseReference.child(user.getUid()).setValue(userInformation);

            Toast.makeText(this, "Information Saved", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        if(v == buttonSave){
            saveUserInformation();
        }
    }
}

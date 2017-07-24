package com.example.beiber.csapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewsignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private EditText editTextFirstName,editTextAddress,editTextTel;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        //initialize views
        databaseReference = FirebaseDatabase.getInstance().getReference("users/profiles");
        buttonRegister =(Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewsignin = (TextView) findViewById(R.id.textViewSignin);

        editTextFirstName = (EditText)findViewById(R.id.editTextFirstName);
        editTextAddress = (EditText)findViewById(R.id.editTextAddress);
        editTextTel = (EditText)findViewById(R.id.editTextTel);

        editTextTel.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(editTextTel.getText().length()!=10)
                {
                    editTextTel.setError("Not Valid");
                }
            }
        });

        //attach onclick listener
        buttonRegister.setOnClickListener(this);
        textViewsignin.setOnClickListener(this);
    }
    //when back button press give a alert box
    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure want to exit");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //user register method
    private void registerUser()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String name = editTextFirstName.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String tel = editTextTel.getText().toString().trim();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(tel) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password))
        {

            Toast.makeText(this, "All fields must be filled",Toast.LENGTH_LONG).show();
        }
        else
            {
            //validation ok
            progressDialog.setMessage("Please wait . . .");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                //user successfully registered and logged in
                                FirebaseUser user = firebaseAuth.getCurrentUser();//get the user
                                ProUpload proUpload = new ProUpload(name, address, tel, null);
                                databaseReference.child(user.getUid()).setValue(proUpload);//save information according to the user

                                Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(), ChatActivity.class));

                            }
                            else
                                {
                                    Toast.makeText(MainActivity.this, "Could Not Register. Please Try Again Later",
                                            Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                        }
                    });
            }
    }
    @Override
    public void onClick(View v)
    {
        if(v == buttonRegister)
        {
            registerUser();
        }
        if(v == textViewsignin)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}


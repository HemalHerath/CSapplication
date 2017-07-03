package com.example.beiber.csapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    private Button buttonEditProfile,buttonDeleteProfile,buttonLogout,buttonChat;
    private FirebaseAuth firebaseAuth;
    private TextView textviewUserEmail;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("clients");

        buttonEditProfile = (Button)findViewById(R.id.buttonEditProfile);
        buttonDeleteProfile = (Button)findViewById(R.id.buttonDeleteProfile);
        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        buttonChat = (Button)findViewById(R.id.buttonChat);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textviewUserEmail = (TextView)findViewById(R.id.textviewUserEmail);

        textviewUserEmail.setText(user.getEmail());

        buttonEditProfile.setOnClickListener(this);
        buttonDeleteProfile.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonChat.setOnClickListener(this);
    }

    public void delete() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential("user@example.com", "password1234");

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Profile.this, "deleted",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(Profile.this, "Not deleted. Try Again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public void removeUserData(){

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).removeValue();
    }

    @Override
    public void onClick(View v) {

        if(v==buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(v==buttonEditProfile){
            startActivity(new Intent(this, ProfileActivity.class));
        }
        if(v==buttonDeleteProfile){
            delete();
            removeUserData();
            finish();
            startActivity(new Intent(this,WelcomeActivity.class));
        }
        if(v==buttonChat){
            startActivity(new Intent(this,ChatActivity.class));
        }

    }
}



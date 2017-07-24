package com.example.beiber.csapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity{

    private TextView textviewUserEmail;
    private ImageView ProImg;
    private TextView nameText,addressText,telText;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textviewUserEmail = (TextView) findViewById(R.id.textviewUserEmail);
        textviewUserEmail.setText(user.getEmail());

        ProImg = (ImageView) findViewById(R.id.ProImg);
        nameText = (TextView) findViewById(R.id.nameText);
        addressText = (TextView) findViewById(R.id.addressText);
        telText = (TextView) findViewById(R.id.telText);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String uid = (String) firebaseAuth.getCurrentUser().getUid();//setting auth uid to uid variable`

        databaseReference = FirebaseDatabase.getInstance().getReference("users/profiles/"+uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String Name =(String)dataSnapshot.child("name").getValue();
                nameText.setText(Name);

                String Address = (String)dataSnapshot.child("address").getValue();
                addressText.setText(Address);

                String Tel = (String)dataSnapshot.child("tel").getValue();
                telText.setText(Tel);

                String Url = (String)dataSnapshot.child("url").getValue();
                Glide.with(getApplicationContext()).load(Url).into(ProImg);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
 }




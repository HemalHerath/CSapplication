package com.example.beiber.csapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Tab3Profile extends Fragment{

    private TextView textviewUserEmail;
    private ImageView ProImg;
    private TextView nameText,addressText,telText;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_layout, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textviewUserEmail = (TextView) rootView.findViewById(R.id.textviewUserEmail);
        textviewUserEmail.setText(user.getEmail());

        ProImg = (ImageView) rootView.findViewById(R.id.ProImg);
        nameText = (TextView) rootView.findViewById(R.id.nameText);
        addressText = (TextView) rootView.findViewById(R.id.addressText);
        telText = (TextView) rootView.findViewById(R.id.telText);

        return rootView;
    }

    @Override
    public void onStart() {
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
                Glide.with(getActivity()).load(Url).into(ProImg);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

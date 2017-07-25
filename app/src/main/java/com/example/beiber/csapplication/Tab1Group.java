package com.example.beiber.csapplication;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tab1Group extends Fragment{

    private DatabaseReference mDatabaseRef;
    private List<ProUpload> imgList;
    private ListView lv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_image_list, container, false);

        imgList = new ArrayList<>();
        lv = (ListView) rootView.findViewById(R.id.listViewImage);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(ProActivity.DATABASE_PATH);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ProUpload img = snapshot.getValue(ProUpload.class);
                    imgList.add(img);
                }
                //init adapter
                adapter = new ImageListAdapter(getActivity(),R.layout.image_item,imgList);
                //set adapter for list view
                lv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });

    }
}

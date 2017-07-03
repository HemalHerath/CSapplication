package com.example.beiber.csapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClientListActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    ListView listViewClients;
    List<userInformation> clientList;
    private Button buttonRetrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_view_layout);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("clients");

        listViewClients = (ListView)findViewById(R.id.listViewClients);
        clientList = new ArrayList<>();

        buttonRetrieve = (Button) findViewById(R.id.buttonRetrieve);

        buttonRetrieve.setOnClickListener(this);
    }

    private void retrieve() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                clientList.clear();
                for(DataSnapshot clientSnapshot : dataSnapshot.getChildren()){
                    userInformation userInformation = clientSnapshot.getValue(userInformation.class);

                    clientList.add(userInformation);
                }

                ClientListHelper adapter = new ClientListHelper(ClientListActivity.this,clientList);
                listViewClients.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(this, "Information Retrieved", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

        if(v == buttonRetrieve){
            retrieve();
        }

    }
}

